package com.api.greenlink.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public interface ExceptionStrategy {
    public abstract boolean canHandle(Exception e);
    ResponseEntity<ErrorResponse> handleException(Exception e, HttpServletRequest request);
}
