package com.pdasilem.songservice.mapper;

import com.pdasilem.songservice.dto.SongMetadataDto;
import com.pdasilem.songservice.model.SongMetadata;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SongMetadataMapper {

    SongMetadata dtoToModel(SongMetadataDto songMetadataDto);

    SongMetadataDto modelToDto(SongMetadata songMetadata);
}
