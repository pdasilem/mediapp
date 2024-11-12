package com.pdasilem.resourceservice.exception;

import lombok.Builder;

@Builder
public record GenericErrorResponse(String message, int status) {
}
