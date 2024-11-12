package com.pdasilem.songservice.mapper;

import com.pdasilem.songservice.dto.SongIdResponse;
import com.pdasilem.songservice.dto.SongIdsResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SongIdMapper {

    default SongIdResponse modelToDto(Integer songId) {
        return new SongIdResponse(songId);
    }

    default SongIdsResponse idListToDto(List<Integer> ids) {
        return new SongIdsResponse(ids);
    }
}
