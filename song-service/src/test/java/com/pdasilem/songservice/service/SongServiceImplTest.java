package com.pdasilem.songservice.service;

import com.pdasilem.songservice.dto.SongIdResponse;
import com.pdasilem.songservice.dto.SongIdsResponse;
import com.pdasilem.songservice.dto.SongMetadataDto;
import com.pdasilem.songservice.dto.SongMetadataRequest;
import com.pdasilem.songservice.exception.MetadataAlreadyExistsException;
import com.pdasilem.songservice.mapper.SongIdMapper;
import com.pdasilem.songservice.mapper.SongMetadataMapper;
import com.pdasilem.songservice.model.SongMetadata;
import com.pdasilem.songservice.repository.SongRepository;
import com.pdasilem.songservice.service.impl.SongServiceImpl;
import com.pdasilem.songservice.util.SongMetadataValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.anyList;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SongServiceImplTest {

    @Mock
    private SongRepository songRepository;

    @Mock
    private SongMetadataMapper songMetadataMapper;

    @Mock
    private SongIdMapper songIdMapper;

    @Mock
    private SongMetadataValidator songMetadataValidator;

    @InjectMocks
    private SongServiceImpl songService;

    private SongMetadataRequest songMetadataRequest;
    private SongMetadataDto songMetadataDto;
    private SongMetadata songMetadata;
    private SongIdResponse songIdResponse;

    @BeforeEach
    void setUp() {
        songMetadataRequest = new SongMetadataRequest("1", "Title", "Artist", "Album", "3:30", "2023");
        songMetadataDto = new SongMetadataDto(1, "Title", "Artist", "Album", "3:30", 2023);

        songMetadata = new SongMetadata();
        songMetadata.setId(1);
        songMetadata.setName("Title");
        songMetadata.setArtist("Artist");
        songMetadata.setAlbum("Album");
        songMetadata.setYear(2023);

        songIdResponse = new SongIdResponse(1);
    }

    @Test
    void saveSongMetadata_shouldThrowException_whenMetadataAlreadyExists() {
        when(songMetadataValidator.validateMetadata(any())).thenReturn(songMetadataDto);
        when(songRepository.existsById(anyInt())).thenReturn(true);

        assertThrows(MetadataAlreadyExistsException.class, () -> songService.saveSongMetadata(songMetadataRequest));
        verify(songRepository, never()).save(any());
    }

    @Test
    void saveSongMetadata_shouldSaveMetadata_whenMetadataDoesNotExist() {
        when(songMetadataValidator.validateMetadata(any())).thenReturn(songMetadataDto);
        when(songRepository.existsById(anyInt())).thenReturn(false);
        when(songRepository.save(any())).thenReturn(songMetadata);
        when(songIdMapper.modelToDto(anyInt())).thenReturn(songIdResponse);

        SongIdResponse response = songService.saveSongMetadata(songMetadataRequest);

        assertNotNull(response);
        assertEquals(1, response.id());
        verify(songRepository).save(any());
    }

    @Test
    void deleteSongMetadataByIds_shouldDeleteMetadata_whenIdsAreValid() {
        List<SongMetadata> songsToDelete = List.of(songMetadata, songMetadata, songMetadata);
        List<Integer> songIdsToDelete = List.of(1, 2, 3);

        when(songRepository.findAllByIdIn(anyList())).thenReturn(songsToDelete);
        when(songIdMapper.idListToDto(anyList())).thenReturn(new SongIdsResponse(songIdsToDelete));

        SongIdsResponse response = songService.deleteSongMetadataByIds("1,2,3");

        assertNotNull(response);
        assertEquals(songIdsToDelete, response.ids());
        verify(songRepository).deleteByIdIn(anyList());
    }
}