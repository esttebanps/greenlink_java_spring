package com.api.greenlink.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public abstract class BaseExceptionStrategy implements ExceptionStrategy {

    public abstract HttpStatus getStatus();

    protected ResponseEntity<ErrorResponse> buildErrorResponse(BaseException e, HttpServletRequest request) {
        ErrorResponse error = ErrorResponse.builder()
                .code(getStatus().value())
                .code_name(getStatus().name())
                .message(e.getMessage())
                .path(request.getRequestURI())
                .build();
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
