package com.pdasilem.resourceservice.exception.response;

import lombok.Builder;

@Builder
public record GenericErrorResponse(String message, int status) {
}
