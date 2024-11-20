package com.pdasilem.resourceservice.service;

import com.pdasilem.resourceservice.dto.DeletedResourceIdsResponse;
import com.pdasilem.resourceservice.dto.IdResponse;
import com.pdasilem.resourceservice.dto.SongIdsResponse;
import com.pdasilem.resourceservice.dto.SongMetadataDto;
import com.pdasilem.resourceservice.exception.ResourceNotFoundException;
import com.pdasilem.resourceservice.model.Mp3Model;
import com.pdasilem.resourceservice.repository.Mp3Repository;
import com.pdasilem.resourceservice.service.impl.Mp3Service;
import com.pdasilem.resourceservice.util.Mp3MetadataExtractor;
import org.apache.tika.metadata.Metadata;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class Mp3ServiceTest {

    @Mock
    private Mp3MetadataExtractor metadataExtractor;

    @Mock
    private Mp3Repository repository;

    @Mock
    private MetadataService metadataService;

    @InjectMocks
    private Mp3Service mp3Service;

    private byte[] audioData;
    private Metadata metadata;
    private Mp3Model mp3Model;

    @BeforeEach
    void setUp() {
        audioData = new byte[]{1, 2, 3};
        metadata = new Metadata();
        metadata.set("xmpDM:duration", "300");
        metadata.set("xmpDM:artist", "Artist");
        metadata.set("xmpDM:album", "Album");
        metadata.set("dc:title", "Title");
        metadata.set("xmpDM:releaseDate", "2023");

        mp3Model = new Mp3Model();
        mp3Model.setId(1);
        mp3Model.setData(audioData);
    }

    @Test
    void saveResource() {
        when(metadataExtractor.extractMetadata(audioData)).thenReturn(metadata);
        when(repository.save(any(Mp3Model.class))).thenReturn(mp3Model);

        IdResponse response = mp3Service.saveResource(audioData);

        assertNotNull(response);
        assertEquals(1, response.id());
        verify(metadataService).sendMetadata(any(SongMetadataDto.class));
    }

    @Test
    void getResourceById() {
        when(repository.findById(1)).thenReturn(Optional.of(mp3Model));

        byte[] result = mp3Service.getResourceById(1);

        assertArrayEquals(audioData, result);
    }

    @Test
    void getResourceById_NotFound() {
        when(repository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> mp3Service.getResourceById(1));
    }

    @Test
    void deleteResourceByIds() {
        List<Integer> ids = Arrays.asList(1, 2, 3);
        List<Mp3Model> deletedModels = ids.stream().map(id -> {
            Mp3Model model = new Mp3Model();
            model.setId(id);
            return model;
        }).toList();
        when(repository.deleteByIdIn(ids)).thenReturn(deletedModels);
        when(metadataService.deleteMetadata(anyString())).thenReturn(new SongIdsResponse(ids));

        DeletedResourceIdsResponse response = mp3Service.deleteResourceByIds("1,2,3");

        assertNotNull(response);
        assertEquals(3, response.ids().size());
        verify(metadataService).deleteMetadata("1,2,3");
    }
}
