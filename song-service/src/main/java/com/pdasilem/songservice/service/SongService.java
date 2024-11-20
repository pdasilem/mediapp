package com.pdasilem.songservice.service;

import com.pdasilem.songservice.dto.SongIdResponse;
import com.pdasilem.songservice.dto.SongIdsResponse;
import com.pdasilem.songservice.dto.SongMetadataDto;
import com.pdasilem.songservice.dto.SongMetadataRequest;

public interface SongService {

    SongIdResponse saveSongMetadata(SongMetadataRequest songMetadataRequest);

    SongMetadataDto getSongMetadataById(Integer id);

    SongIdsResponse deleteSongMetadataByIds(String ids);
}
