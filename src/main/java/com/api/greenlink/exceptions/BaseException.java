package com.api.greenlink.exceptions;

import org.springframework.stereotype.Component;

@Component
public abstract class BaseException extends RuntimeException {
    public BaseException(String message) {
        super(message);
    }
}
