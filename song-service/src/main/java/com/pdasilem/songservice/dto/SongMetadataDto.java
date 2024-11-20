package com.pdasilem.songservice.dto;

public record SongMetadataDto(
        Integer id,
        String name,
        String artist,
        String album,
        String duration,
        Integer year) {
}
