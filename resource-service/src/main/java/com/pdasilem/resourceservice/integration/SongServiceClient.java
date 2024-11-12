package com.pdasilem.resourceservice.integration;

import com.pdasilem.resourceservice.dto.SongIdResponse;
import com.pdasilem.resourceservice.dto.SongIdsResponse;
import com.pdasilem.resourceservice.dto.SongMetadataDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "${song-service.name}", url = "${song-service.url}")
public interface SongServiceClient {

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    SongIdResponse sendMetadata(@RequestBody SongMetadataDto songMetadataDto);

    @DeleteMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    SongIdsResponse deleteMetadata(@RequestParam("id") String id);
}
