package com.pdasilem.songservice.service;

import com.pdasilem.songservice.dto.SongIdResponse;
import com.pdasilem.songservice.dto.SongIdsResponse;
import com.pdasilem.songservice.dto.SongMetadataDto;
import com.pdasilem.songservice.mapper.SongIdMapper;
import com.pdasilem.songservice.mapper.SongMetadataMapper;
import com.pdasilem.songservice.model.SongMetadata;
import com.pdasilem.songservice.repository.SongRepository;
import com.pdasilem.songservice.service.impl.SongServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SongServiceImplTest {
    @Mock
    private SongRepository songRepository;

    @Mock
    private SongMetadataMapper songMetadataMapper;

    @Mock
    private SongIdMapper songIdMapper;

    @InjectMocks
    private SongServiceImpl songService;

    @Test
    void testSaveSongMetadata_Success() {
        // Arrange
        SongMetadataDto songMetadataDto = new SongMetadataDto("Song", "Artist", "Album", "3:30", 1, 2023);
        SongMetadata songMetadata = new SongMetadata();
        songMetadata.setId(1);
        when(songMetadataMapper.dtoToModel(any(SongMetadataDto.class))).thenReturn(songMetadata);
        when(songRepository.save(any(SongMetadata.class))).thenReturn(songMetadata);
        when(songIdMapper.modelToDto(any(Integer.class))).thenReturn(new SongIdResponse(1));

        // Act
        SongIdResponse result = songService.saveSongMetadata(songMetadataDto);

        // Assert
        assertEquals(1, result.id());
    }

    @Test
    void testSaveSongMetadata_Error() {
        // Arrange
        SongMetadataDto songMetadataDto = new SongMetadataDto("Song", "Artist", "Album", "3:30", 1, 2023);
        when(songMetadataMapper.dtoToModel(any(SongMetadataDto.class))).thenReturn(null);

        // Act and Assert
        assertThrows(NullPointerException.class, () -> songService.saveSongMetadata(songMetadataDto));
    }

    @Test
    void testGetSongMetadataById_Success() {
        // Arrange
        Integer id = 1;
        SongMetadata songMetadata = new SongMetadata();
        songMetadata.setId(id);
        SongMetadataDto songMetadataDto = new SongMetadataDto("Song", "Artist", "Album", "3:30", 1, 2023);
        when(songRepository.getById(any(Integer.class))).thenReturn(songMetadata);
        when(songMetadataMapper.modelToDto(any(SongMetadata.class))).thenReturn(songMetadataDto);

        // Act
        Optional<SongMetadataDto> result = songService.getSongMetadataById(id);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(songMetadataDto, result.get());
    }

    @Test
    void testGetSongMetadataById_NotFound() {
        // Arrange
        Integer id = 1;
        when(songRepository.getById(any(Integer.class))).thenReturn(null);

        // Act
        Optional<SongMetadataDto> result = songService.getSongMetadataById(id);

        // Assert
        assertTrue(result.isEmpty());
    }

    @Test
    void testDeleteSongMetadataByIds_Success() {
        // Arrange
        String ids = "1,2,3";
        List<Integer> idsList = Arrays.stream(ids.split(",")).map(Integer::valueOf).toList();
        List<SongMetadata> songsToDelete = List.of(new SongMetadata(), new SongMetadata(), new SongMetadata());
        when(songRepository.findAllByIds(anyList())).thenReturn(songsToDelete);
        when(songIdMapper.idListToDto(anyList())).thenReturn(new SongIdsResponse(idsList));

        // Act
        SongIdsResponse result = songService.deleteSongMetadataByIds(ids);

        // Assert
        assertEquals(idsList, result.ids());
    }

    @Test
    void testDeleteSongMetadataByIds_EmptyIds() {
        // Arrange
        String ids = "";

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> songService.deleteSongMetadataByIds(ids));
    }
}