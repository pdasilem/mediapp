package com.pdasilem.songservice.exception;

import com.pdasilem.songservice.exception.response.GenericErrorResponse;
import com.pdasilem.songservice.exception.response.GenericErrorResponseWithDetails;
import com.pdasilem.songservice.exception.response.InvalidatedErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({Exception.class, RuntimeException.class})
    public ResponseEntity<GenericErrorResponse> handleCommonException(Exception exception) {
        return buildErrorResponse("An error occurred on the server: " + exception.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<GenericErrorResponse> handleResourceNotFoundException(ResourceNotFoundException exception) {
        return buildErrorResponse(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MetadataAlreadyExistsException.class)
    public ResponseEntity<GenericErrorResponse> handleMetadataAlreadyExistsException(MetadataAlreadyExistsException exception) {
        return buildErrorResponse(exception.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvalidCsvException.class)
    public ResponseEntity<GenericErrorResponse> handleBadRequestExceptions(Exception exception) {
        return buildErrorResponse(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<InvalidatedErrorResponse> handleValidationExceptions(
            MethodArgumentNotValidException exception) {
        Map<String, String> fieldErrors = new HashMap<>();
        for (FieldError error : exception.getBindingResult().getFieldErrors()) {
            fieldErrors.put(error.getField(), error.getDefaultMessage());
        }

        var response = InvalidatedErrorResponse
                .builder()
                .message("Song metadata missing validation error")
                .status(HttpStatus.BAD_REQUEST.value())
                .errors(fieldErrors)
                .build();

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<GenericErrorResponseWithDetails> handleValidationException(ValidationException exception) {
        return buildErrorResponseWithDetails(exception.getErrorMessage(),
                exception.getErrorDetails());
    }

    private ResponseEntity<GenericErrorResponse> buildErrorResponse(String message, HttpStatus status) {
        var response = GenericErrorResponse.builder()
                .message(message)
                .status(status.value())
                .build();
        return new ResponseEntity<>(response, status);
    }

    private ResponseEntity<GenericErrorResponseWithDetails> buildErrorResponseWithDetails(String message,
                                                                                          Map<String, String> details) {
        var response = GenericErrorResponseWithDetails.builder()
                .message(message)
                .details(details)
                .status(HttpStatus.BAD_REQUEST.value())
                .build();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
