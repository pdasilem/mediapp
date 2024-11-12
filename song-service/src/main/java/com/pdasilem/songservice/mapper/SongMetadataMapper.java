package com.pdasilem.songservice.mapper;

import com.pdasilem.songservice.dto.SongMetadataDto;
import com.pdasilem.songservice.model.SongMetadata;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface SongMetadataMapper {
    SongMetadata dtoToModel(SongMetadataDto songMetadataDto);

    SongMetadataDto modelToDto(SongMetadata songMetadata);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    SongMetadata partialUpdate(SongMetadataDto songMetadataDto, @MappingTarget SongMetadata songMetadata);
}
