package com.api.greenlink.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Configuration
@RestControllerAdvice
public class ControllerAdvice {

    private final List<ExceptionStrategy> strategies;

    public ControllerAdvice(List<ExceptionStrategy> strategies) {
        this.strategies = strategies;
    }

    @ExceptionHandler(Exception.class)
    public  ResponseEntity<ErrorResponse> handlerException(Exception e, HttpServletRequest request){
        for (ExceptionStrategy strategy : strategies) {
            if (strategy.canHandle(e)) {
                return strategy.handleException(e, request);
            }
        }

        ErrorResponse error = ErrorResponse.builder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .code_name(HttpStatus.INTERNAL_SERVER_ERROR.name())
                .message("An unexpected error occurred")
                .build();
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }







/*
    @ExceptionHandler({
            IllegalArgumentException.class,
            HttpMessageNotReadableException.class,
            NullPointerException.class,
            IllegalAccessException.class}
    )
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorResponse> handleBadRequest(
            Exception e,
            HttpServletRequest request
    ) {
        String message = e.getMessage();

        if (e instanceof IllegalArgumentException) {
            message = "error: " + e.getMessage();
        } else if (e instanceof HttpMessageNotReadableException) {
            message = "error: " + e.getMessage();
        } else if (e instanceof NullPointerException) {
            message = "error: " + e.getMessage();
        }

        ErrorResponse error = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.name(),
                message,
                request.getRequestURI()
        );

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleValidationError(
            ConstraintViolationException e,
            HttpServletRequest request
    ){

        String message = "Validaciones de campos";

        Map<String, String> errors = new HashMap<>();

        e.getConstraintViolations().forEach(violation -> {
            String fieldName = violation.getPropertyPath().toString();
            String errorMessage = violation.getMessage();
            errors.put(fieldName, errorMessage);
        });

        ErrorResponse error = new ErrorResponse(
                HttpStatus.NO_CONTENT.value(),
                HttpStatus.BAD_REQUEST.name(),
                message,
                errors,
                LocalDateTime.now(),
                request.getRequestURI()
        );

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> handleNotFoundException(
            NotFoundException e,
            HttpServletRequest request
    ) {
        String message = "Recurso no encontrado";

        ErrorResponse error = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.name(),
                e.getMessage()==null ? message : e.getMessage(),
                request.getRequestURI()
        );

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({ InvalidCredentialsException.class, AccessDeniedException.class, AuthenticationException.class })
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public ResponseEntity<ErrorResponse> handleEntityNotFoundException(
            Exception e,
            HttpServletRequest request
    ) {
        String message;
        if (e instanceof InvalidCredentialsException) {
            message = "Credenciales inválidas"; // Specific message for InvalidCredentialsException
        } else if (e instanceof AccessDeniedException) {
            message = "Acceso denegado. No tiene permisos para realizar esta acción."; // Specific message for AccessDeniedException
        } else {
            message = "Error de autenticación"; // Generic message for AuthenticationException
        }
        ErrorResponse error = new ErrorResponse(
                HttpStatus.UNAUTHORIZED.value(),
                HttpStatus.UNAUTHORIZED.name(),
                e.getMessage()==null ? message : e.getMessage(),
                request.getRequestURI()
        );

        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }*/
}

