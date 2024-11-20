package com.pdasilem.songservice.dto;

import jakarta.validation.constraints.NotBlank;

public record SongMetadataRequest(
        @NotBlank
        String id,
        @NotBlank(message = "Title can't be empty")
        String name,
        @NotBlank(message = "Artist can't be empty")
        String artist,
        @NotBlank(message = "Album can't be empty")
        String album,
        @NotBlank(message = "Length can't be empty")
        String duration,
        @NotBlank(message = "Year can't be null")
        String year) {
}
