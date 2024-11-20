package com.pdasilem.resourceservice.controller;

import com.pdasilem.resourceservice.controller.impl.Mp3Controller;
import com.pdasilem.resourceservice.dto.DeletedResourceIdsResponse;
import com.pdasilem.resourceservice.dto.IdResponse;
import com.pdasilem.resourceservice.service.ResourceService;
import com.pdasilem.resourceservice.util.CsvValidator;
import com.pdasilem.resourceservice.util.Mp3Validator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class Mp3ControllerTest {

    @Mock
    private ResourceService resourceService;

    @Mock
    private Mp3Validator mp3Validator;

    @Mock
    private CsvValidator csvValidator;

    @InjectMocks
    private Mp3Controller mp3Controller;

    @Test
    void testUploadResource() {
        byte[] audioData = new byte[]{1, 2, 3};
        IdResponse response = new IdResponse(1);
        when(resourceService.saveResource(any(byte[].class))).thenReturn(response);

        ResponseEntity<IdResponse> result = mp3Controller.uploadResource(audioData);

        assertEquals(ResponseEntity.ok(response), result);
        verify(mp3Validator).validateAudioData(audioData);
        verify(resourceService).saveResource(audioData);
    }

    @Test
    void testGetResourceById() {
        Integer id = 1;
        byte[] audioData = new byte[]{1, 2, 3};
        when(resourceService.getResourceById(id)).thenReturn(audioData);

        ResponseEntity<byte[]> result = mp3Controller.getResourceById(id);

        assertEquals(ResponseEntity.ok(audioData), result);
        verify(resourceService).getResourceById(id);
    }

    @Test
    void testDeleteResourcesByIds() {
        String ids = "1,2,3";
        DeletedResourceIdsResponse response = new DeletedResourceIdsResponse(List.of(1, 2, 3));
        when(resourceService.deleteResourceByIds(ids)).thenReturn(response);

        ResponseEntity<DeletedResourceIdsResponse> result = mp3Controller.deleteResourcesByIds(ids);

        assertEquals(ResponseEntity.ok(response), result);
        verify(csvValidator).validate(ids);
        verify(resourceService).deleteResourceByIds(ids);
    }
}
