package com.example.taskmanager.exception;

import com.example.taskmanager.dto.ApiErrorResponse;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValidationException(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        Map<String, String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        error -> error.getField(),
                        error -> error.getDefaultMessage(),
                        (first, second) -> first
                ));

        ApiErrorResponse response = new ApiErrorResponse(
                Instant.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Validation Failed",
                "Please check your request fields",
                request.getRequestURI(),
                errors
        );

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(IllegalArgumentException.class)
public ResponseEntity<ApiErrorResponse> handleIllegalArgument(
        IllegalArgumentException ex,
        HttpServletRequest request) {

    ApiErrorResponse response = new ApiErrorResponse(
            Instant.now(),
            400,
            "Bad Request",
            ex.getMessage(),
            request.getRequestURI(),
            Map.of()
    );

    return ResponseEntity.badRequest().body(response);
}
@ExceptionHandler(TaskNotFoundException.class)
public ResponseEntity<ApiErrorResponse> handleTaskNotFound(
        TaskNotFoundException ex,
        HttpServletRequest request) {

    ApiErrorResponse response = new ApiErrorResponse(
            Instant.now(),
            HttpStatus.NOT_FOUND.value(),
            "Not Found",
            ex.getMessage(),
            request.getRequestURI(),
            Map.of()
    );

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
}

@ExceptionHandler(DuplicateTagException.class)
public ResponseEntity<ApiErrorResponse> handleDuplicateTag(
        DuplicateTagException ex,
        HttpServletRequest request) {

    ApiErrorResponse response = new ApiErrorResponse(
        Instant.now(),
        HttpStatus.CONFLICT.value(),
        "Conflict",
        ex.getMessage(),
        request.getRequestURI(),
        Map.of()
    );

    return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
}
}