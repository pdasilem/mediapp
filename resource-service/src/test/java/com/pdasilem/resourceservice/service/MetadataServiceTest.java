package com.pdasilem.resourceservice.service;

import com.pdasilem.resourceservice.dto.SongIdResponse;
import com.pdasilem.resourceservice.dto.SongMetadataDto;
import com.pdasilem.resourceservice.integration.SongServiceClient;
import com.pdasilem.resourceservice.service.MetadataService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MetadataServiceTest {

    @Mock
    private SongServiceClient songServiceClient;

    @InjectMocks
    private MetadataService metadataService;

    private SongMetadataDto songMetadataDto;
    private SongIdResponse songIdResponse;

    @BeforeEach
    void setUp() {
        songMetadataDto = SongMetadataDto.builder()
                .resourceId(1)
                .artist("Artist")
                .album("Album")
                .name("Song")
                .length("3:45")
                .year(2021)
                .build();

        songIdResponse = new SongIdResponse(1);
    }

    @Test
    void testSendMetadata() {
        when(songServiceClient.sendMetadata(any(SongMetadataDto.class))).thenReturn(songIdResponse);

        SongIdResponse response = metadataService.sendMetadata(songMetadataDto);

        assertEquals(songIdResponse, response);
    }
}
