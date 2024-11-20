package com.pdasilem.songservice.exception.response;

import lombok.Builder;

import java.util.Map;

@Builder
public record GenericErrorResponseWithDetails(String message, Map<String, String> details, int status) {
}
