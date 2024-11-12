package com.pdasilem.resourceservice.service;

import com.pdasilem.resourceservice.dto.DeletedResourcesResponse;
import com.pdasilem.resourceservice.dto.ResourceIdResponse;

public interface ResourceService {

    ResourceIdResponse saveResource(byte[] audioData);

    byte[] getResourceById(Integer id);

    DeletedResourcesResponse deleteResourceByIds(String id);
}
