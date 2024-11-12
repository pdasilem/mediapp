package com.pdasilem.songservice.exception;

import lombok.Builder;

@Builder
public record GenericErrorResponse(String message, int status) {
}
