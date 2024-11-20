package com.pdasilem.resourceservice.service.impl;

import com.pdasilem.resourceservice.dto.DeletedResourceIdsResponse;
import com.pdasilem.resourceservice.dto.IdResponse;
import com.pdasilem.resourceservice.dto.SongMetadataDto;
import com.pdasilem.resourceservice.exception.InternalServerException;
import com.pdasilem.resourceservice.exception.ResourceNotFoundException;
import com.pdasilem.resourceservice.model.Mp3Model;
import com.pdasilem.resourceservice.repository.Mp3Repository;
import com.pdasilem.resourceservice.service.MetadataService;
import com.pdasilem.resourceservice.service.ResourceService;
import com.pdasilem.resourceservice.util.Mp3MetadataExtractor;
import lombok.RequiredArgsConstructor;
import org.apache.tika.metadata.Metadata;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class Mp3Service implements ResourceService {

    private final Mp3MetadataExtractor metadataExtractor;
    private final Mp3Repository repository;
    private final MetadataService metadataService;

    @Transactional
    @Override
    public IdResponse saveResource(byte[] audioData) {

        var mp3Model = createMp3Model(audioData);
        var savedResourceId = repository.save(mp3Model).getId();

        var metadataDto = createMetadataDto(savedResourceId,
                metadataExtractor.extractMetadata(audioData));
        metadataService.sendMetadata(metadataDto);
        return new IdResponse(savedResourceId);
    }

    @Override
    public byte[] getResourceById(Integer id) {
        return repository.findById(id).map(Mp3Model::getData)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Resource with ID=%s not found", id)));
    }

    @Transactional
    @Override
    public DeletedResourceIdsResponse deleteResourceByIds(String id) {

        var idsList = Arrays.stream(id.split(","))
                .map(Integer::valueOf)
                .toList();

        var deletedIds = repository.deleteByIdIn(idsList).stream().map(Mp3Model::getId).toList();
        var deletedSongIdsResponse = metadataService.deleteMetadata(id);
        var deletedSongIds = deletedSongIdsResponse.ids();

        if (!Objects.equals(deletedIds, deletedSongIds)) {
            throw new InternalServerException("Ids deletion operation failed");
        }
        return new DeletedResourceIdsResponse(deletedIds);
    }

    private Mp3Model createMp3Model(byte[] data) {
        var model = new Mp3Model();
        model.setData(data);
        return model;
    }

    private SongMetadataDto createMetadataDto(Integer resourceId, Metadata metadata) {
        return SongMetadataDto.builder()
                .id(resourceId)
                .artist(metadata.get("xmpDM:artist"))
                .album(metadata.get("xmpDM:album"))
                .name(metadata.get("dc:title"))
                .duration(metadata.get("xmpDM:duration"))
                .year(metadata.get("xmpDM:releaseDate"))
                .build();
    }
}
