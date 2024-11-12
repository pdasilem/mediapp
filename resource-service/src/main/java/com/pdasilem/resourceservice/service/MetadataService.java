package com.pdasilem.resourceservice.service;

import com.pdasilem.resourceservice.dto.SongIdsResponse;
import com.pdasilem.resourceservice.integration.SongServiceClient;
import com.pdasilem.resourceservice.dto.SongIdResponse;
import com.pdasilem.resourceservice.dto.SongMetadataDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.NOT_SUPPORTED)
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
