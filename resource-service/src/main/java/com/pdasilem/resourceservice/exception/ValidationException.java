package com.pdasilem.resourceservice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@AllArgsConstructor
@Getter
public class ValidationException extends RuntimeException{

    private final String errorMessage;
    private final Map<String, String> errorDetails;
}
