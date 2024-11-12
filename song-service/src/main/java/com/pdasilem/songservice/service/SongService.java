package com.pdasilem.songservice.service;

import com.pdasilem.songservice.dto.SongIdResponse;
import com.pdasilem.songservice.dto.SongIdsResponse;
import com.pdasilem.songservice.dto.SongMetadataDto;

import java.util.Optional;

public interface SongService {

    SongIdResponse saveSongMetadata(SongMetadataDto songMetadataDto);

    Optional<SongMetadataDto> getSongMetadataById(Integer id);

    SongIdsResponse deleteSongMetadataByIds(String ids);
}
