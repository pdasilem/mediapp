package com.pdasilem.resourceservice.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<GenericErrorResponse> handleCommonException(Exception exception) {
        return buildErrorResponse("An internal server error has occurred: " + exception.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<GenericErrorResponse> handleResourceNotFoundException(ResourceNotFoundException exception) {
        return buildErrorResponse(exception.getMessage(),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DataExtractionException.class)
    public ResponseEntity<GenericErrorResponse> handleExtractionException(DataExtractionException exception) {
        return buildErrorResponse(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({InvalidMp3Exception.class, InvalidCsvException.class})
    public ResponseEntity<GenericErrorResponse> handleBadRequestExceptions(Exception exception) {
        return buildErrorResponse(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<GenericErrorResponse> buildErrorResponse(String message, HttpStatus status) {
        var response = GenericErrorResponse.builder()
                .message(message)
                .status(status.value())
                .build();
        return new ResponseEntity<>(response, status);
    }
}