package com.pdasilem.resourceservice.service.impl;

import com.pdasilem.resourceservice.dto.DeletedResourcesResponse;
import com.pdasilem.resourceservice.dto.ResourceIdResponse;
import com.pdasilem.resourceservice.dto.SongMetadataDto;
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

        @Service
        @RequiredArgsConstructor
        public class Mp3Service implements ResourceService {

            private final Mp3MetadataExtractor metadataExtractor;
            private final Mp3Repository repository;
            private final MetadataService metadataService;

            @Transactional
            @Override
            public ResourceIdResponse saveResource(byte[] audioData) {

                var metadata = metadataExtractor.extractMetadata(audioData);
                var mp3Model = createMp3Model(audioData);
                var savedRes = repository.save(mp3Model);
                var savedResId = savedRes.getId();
                var durationInMinSec = convertDuration(metadata.get("xmpDM:duration"));

                var metadataDto = createMetadataDto(savedResId, metadata, durationInMinSec);
                metadataService.sendMetadata(metadataDto);
                return new ResourceIdResponse(savedResId);
            }

            @Override
            public byte[] getResourceById(Integer id) {
                return repository.findById(id).map(Mp3Model::getData)
                        .orElseThrow(() -> new ResourceNotFoundException(
                                String.format("The resource with the specified %s does not exist", id)));
            }

            @Transactional
            @Override
            public DeletedResourcesResponse deleteResourceByIds(String id) {

                var idsList = Arrays.stream(id.split(","))
                        .map(Integer::valueOf)
                        .toList();

                var deletedIds = repository.deleteByIdIn(idsList).stream().map(Mp3Model::getId).toList();
                metadataService.deleteMetadata(id);

                return new DeletedResourcesResponse(deletedIds);
            }

            private Mp3Model createMp3Model(byte[] data) {
                var model = new Mp3Model();
                model.setData(data);
                return model;

            }

            private String convertDuration(String duration) {
                var length = Double.parseDouble(duration);
                var minutes = (int) length / 60;
                var seconds = (int) length % 60;
                return minutes + ":" + (seconds > 9 ? seconds : "0" + seconds);
            }

            private SongMetadataDto createMetadataDto(Integer savedResult, Metadata metadata, String durationInMinSec) {
                return SongMetadataDto.builder()
                        .resourceId(savedResult)
                        .artist(metadata.get("xmpDM:artist"))
                        .album(metadata.get("xmpDM:album"))
                        .name(metadata.get("dc:title"))
                        .length(durationInMinSec)
                        .year(Integer.parseInt(metadata.get("xmpDM:releaseDate")))
                        .build();
            }
        }
