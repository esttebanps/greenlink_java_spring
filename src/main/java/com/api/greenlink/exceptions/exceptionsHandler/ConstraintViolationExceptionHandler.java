package com.api.greenlink.exceptions.exceptionsHandler;


import com.api.greenlink.exceptions.BaseExceptionStrategy;
import com.api.greenlink.exceptions.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ConstraintViolationExceptionHandler extends BaseExceptionStrategy {

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.BAD_REQUEST;
    }

    @Override
    public boolean canHandle(Exception e) {
        return e instanceof ConstraintViolationException;
    }

    @Override
    public ResponseEntity<ErrorResponse> handleException(Exception e, HttpServletRequest request) {
        ConstraintViolationException cve = (ConstraintViolationException) e;

        Map<String, String> errors = new HashMap<>();
        cve.getConstraintViolations().forEach(violation -> {
            String fieldName = violation.getPropertyPath().toString();
            String errorMessage = violation.getMessage();
            errors.put(fieldName, errorMessage);
        });

        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .code_name(HttpStatus.BAD_REQUEST.name())
                .message("Validation failed")
                .details(errors)
                .path(request.getRequestURI())
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
