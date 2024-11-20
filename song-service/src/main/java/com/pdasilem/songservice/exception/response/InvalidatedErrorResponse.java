package com.pdasilem.songservice.exception.response;

import lombok.Builder;

import java.util.Map;

@Builder
public record InvalidatedErrorResponse(String message, int status, Map<String, String> errors) {
}
