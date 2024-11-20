package com.pdasilem.resourceservice.dto;

import lombok.Builder;

@Builder
public record SongMetadataDto(Integer id, String name, String artist, String album, String duration,
                              String year) {
}
