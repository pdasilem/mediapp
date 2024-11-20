package com.pdasilem.resourceservice.service;

import com.pdasilem.resourceservice.dto.DeletedResourceIdsResponse;
import com.pdasilem.resourceservice.dto.IdResponse;

public interface ResourceService {

    IdResponse saveResource(byte[] audioData);

    byte[] getResourceById(Integer id);

    DeletedResourceIdsResponse deleteResourceByIds(String id);
}
