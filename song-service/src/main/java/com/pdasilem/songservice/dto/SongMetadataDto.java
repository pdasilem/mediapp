package com.pdasilem.songservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record SongMetadataDto(
        @NotBlank(message = "Title can't be empty")
        String name,
        @NotBlank(message = "Artist can't be empty")
        String artist,
        @NotBlank(message = "Album can't be empty")
        String album,
        @NotBlank(message = "Length can't be empty")
        String length,
        @NotNull(message = "Resource Id can't be null")
        @Positive(message = "Resource Id should be positive")
        Integer resourceId,
        @NotNull(message = "Year can't be null")
        @Positive(message = "Year should be positive")
        Integer year) {
}
