package com.pdasilem.resourceservice.dto;

import lombok.Builder;

@Builder
public record SongMetadataDto(String name, String artist, String album, String length, Integer resourceId,
                              Integer year) {
}
