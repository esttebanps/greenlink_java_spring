package com.api.greenlink.exceptions.exceptionsHandler;

import com.api.greenlink.exceptions.exception.BadRequestException;
import com.api.greenlink.exceptions.BaseExceptionStrategy;
import com.api.greenlink.exceptions.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class BadRequestExceptionHandler extends BaseExceptionStrategy {

    @Override
    public ResponseEntity<ErrorResponse> handleException(Exception e, HttpServletRequest request) {
        return buildErrorResponse((BadRequestException)e, request);
    }

    @Override
    public boolean canHandle(Exception e) {
        return e instanceof BadRequestException;
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
