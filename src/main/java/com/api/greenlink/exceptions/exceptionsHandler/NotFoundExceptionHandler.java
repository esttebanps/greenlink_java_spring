package com.api.greenlink.exceptions.exceptionsHandler;

import com.api.greenlink.exceptions.BaseExceptionStrategy;
import com.api.greenlink.exceptions.ErrorResponse;
import com.api.greenlink.exceptions.exception.NotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class NotFoundExceptionHandler extends BaseExceptionStrategy {
    @Override
    public ResponseEntity<ErrorResponse> handleException(Exception e, HttpServletRequest request) {
        return buildErrorResponse((NotFoundException) e, request);
    }

    public boolean canHandle(Exception e) {
        return e instanceof NotFoundException;
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.NOT_FOUND;
    }
}
