package com.api.greenlink.config.error;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.*;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler({
            IllegalArgumentException.class,
            HttpMessageNotReadableException.class,
            NullPointerException.class,
            IllegalAccessException.class}
    )
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorResponse> handleBadRequest(Exception e) {
        String message = e.getMessage();

        if (e instanceof IllegalArgumentException) {
            message = "error: " + e.getMessage();
        } else if (e instanceof HttpMessageNotReadableException) {
            message = "error: " + e.getMessage();
        } else if (e instanceof NullPointerException) {
            message = "error: " + e.getMessage();
        }

        ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST.name(), message);

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleValidationError(ConstraintViolationException e){

        String message = "Validaciones de campos";

        Map<String, String> errors = new HashMap<>();

        e.getConstraintViolations().forEach(violation -> {
            String fieldName = violation.getPropertyPath().toString();
            String errorMessage = violation.getMessage();
            errors.put(fieldName, errorMessage);
        });

        ErrorResponse error = new ErrorResponse(
                HttpStatus.BAD_REQUEST.name(),
                message,
                errors
        );

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException e) {
        String message = "Recurso no encontrado";

        ErrorResponse error = new ErrorResponse(
                HttpStatus.NOT_FOUND.name(),
                message
        );

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}

