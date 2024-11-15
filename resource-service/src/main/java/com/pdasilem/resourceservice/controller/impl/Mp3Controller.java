package com.pdasilem.resourceservice.controller.impl;

import com.pdasilem.resourceservice.controller.ResourceController;
import com.pdasilem.resourceservice.dto.DeletedResourcesResponse;
import com.pdasilem.resourceservice.dto.ResourceIdResponse;
import com.pdasilem.resourceservice.service.ResourceService;
import com.pdasilem.resourceservice.util.CsvValidator;
import com.pdasilem.resourceservice.util.Mp3Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class Mp3Controller implements ResourceController {

    private final ResourceService resourceService;
    private final Mp3Validator mp3Validator;
    private final CsvValidator csvValidator;

    @Override
    public ResponseEntity<ResourceIdResponse> uploadResource(byte[] audioData) {
        mp3Validator.validateAudioData(audioData);
        return ResponseEntity.ok(resourceService.saveResource(audioData));
    }

    @Override
    public ResponseEntity<byte[]> getResourceById(Integer id) {
        mp3Validator.validatePositiveId(id);
        return ResponseEntity.ok(resourceService.getResourceById(id));
    }

    @Override
    public ResponseEntity<DeletedResourcesResponse> deleteResourcesByIds(String id) {
        csvValidator.validate(id);
        return ResponseEntity.ok(resourceService.deleteResourceByIds(id));
    }
}
