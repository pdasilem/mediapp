package com.pdasilem.songservice.service.impl;

import com.pdasilem.songservice.dto.SongIdResponse;
import com.pdasilem.songservice.dto.SongIdsResponse;
import com.pdasilem.songservice.dto.SongMetadataDto;
import com.pdasilem.songservice.dto.SongMetadataRequest;
import com.pdasilem.songservice.exception.MetadataAlreadyExistsException;
import com.pdasilem.songservice.exception.ResourceNotFoundException;
import com.pdasilem.songservice.mapper.SongIdMapper;
import com.pdasilem.songservice.mapper.SongMetadataMapper;
import com.pdasilem.songservice.model.SongMetadata;
import com.pdasilem.songservice.repository.SongRepository;
import com.pdasilem.songservice.service.SongService;
import com.pdasilem.songservice.util.SongMetadataValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SongServiceImpl implements SongService {

    private final SongRepository songRepository;
    private final SongMetadataMapper songMetadataMapper;
    private final SongIdMapper songIdMapper;
    private final SongMetadataValidator songMetadataValidator;

    @Override
    @Transactional
    public SongIdResponse saveSongMetadata(SongMetadataRequest songMetadataRequest) {
        var songMetadataDto = songMetadataValidator.validateMetadata(songMetadataRequest);
        if (songRepository.existsById(songMetadataDto.id())) {
            throw new MetadataAlreadyExistsException(String.format("Metadata for this ID=%s already exists.",
                    songMetadataDto.id()));
        }
        var result = songRepository.save(songMetadataMapper.dtoToModel(songMetadataDto));
        return songIdMapper.modelToDto(result.getId());
    }

    @Override
    public SongMetadataDto getSongMetadataById(Integer id) {
        return songMetadataMapper.modelToDto(songRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(String.format("Metadata with ID=%s not found", id))
        ));
    }

    @Transactional
    @Override
    public SongIdsResponse deleteSongMetadataByIds(String ids) {

        List<Integer> idsList = Arrays.stream(ids.split(","))
                .map(Integer::valueOf)
                .toList();

        var songsToDelete = songRepository.findAllByIdIn(idsList);
        var songIdsToDelete = songsToDelete.stream()
                        .map(SongMetadata::getId)
                        .toList();

        songRepository.deleteByIdIn(idsList);

        return songIdMapper.idListToDto(songIdsToDelete);
    }
}
