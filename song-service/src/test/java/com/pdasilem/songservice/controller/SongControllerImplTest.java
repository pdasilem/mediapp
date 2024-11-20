package com.pdasilem.songservice.controller;

import com.pdasilem.songservice.controller.impl.SongControllerImpl;
import com.pdasilem.songservice.dto.SongIdResponse;
import com.pdasilem.songservice.dto.SongIdsResponse;
import com.pdasilem.songservice.dto.SongMetadataDto;
import com.pdasilem.songservice.dto.SongMetadataRequest;
import com.pdasilem.songservice.service.SongService;
import com.pdasilem.songservice.util.CsvValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SongControllerImplTest {

    @Mock
    private SongService songService;

    @Mock
    private CsvValidator csvValidator;

    @InjectMocks
    private SongControllerImpl songController;

    private SongMetadataDto songMetadataDto;

    private SongMetadataRequest songMetadataRequest;

    @BeforeEach
    void setUp() {
        songMetadataDto = new SongMetadataDto(123, "Artist", "Album", "3:30", "1", 2023);
        songMetadataRequest = new SongMetadataRequest("123", "Artist", "Album", "3:30", "1", "2023");
    }

    @Test
    void testPostSong_Success() {
        SongIdResponse songIdResponse = new SongIdResponse(1);
        when(songService.saveSongMetadata(any(SongMetadataRequest.class))).thenReturn(songIdResponse);

        ResponseEntity<SongIdResponse> response = songController.postSong(songMetadataRequest);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(songIdResponse, response.getBody());
    }

    @Test
    void testGetSongById_Success() {
        Integer id = 1;
        when(songService.getSongMetadataById(id)).thenReturn(songMetadataDto);

        ResponseEntity<SongMetadataDto> response = songController.getSongById(id);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(songMetadataDto, response.getBody());
    }

    @Test
    void testDeleteSongByIds_Success() {
        String ids = "1,2,3";
        SongIdsResponse songIdsResponse = new SongIdsResponse(List.of(1, 2, 3));
        doNothing().when(csvValidator).validate(ids);
        when(songService.deleteSongMetadataByIds(ids)).thenReturn(songIdsResponse);

        ResponseEntity<SongIdsResponse> response = songController.deleteSongByIds(ids);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(songIdsResponse, response.getBody());
    }
}
