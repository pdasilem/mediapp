package com.pdasilem.resourceservice.service;

import com.pdasilem.resourceservice.dto.SongIdResponse;
import com.pdasilem.resourceservice.dto.SongIdsResponse;
import com.pdasilem.resourceservice.dto.SongMetadataDto;
import com.pdasilem.resourceservice.integration.SongServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MetadataService {

    private final SongServiceClient songServiceClient;

    public SongIdResponse sendMetadata(SongMetadataDto songMetadataDto) {
        return songServiceClient.sendMetadata(songMetadataDto);
    }

    public SongIdsResponse deleteMetadata(String id) {
        return songServiceClient.deleteMetadata(id);
    }
}
