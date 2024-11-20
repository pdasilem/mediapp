package com.pdasilem.songservice.exception.response;

import lombok.Builder;

@Builder
public record GenericErrorResponse(String message, int status) {
}
