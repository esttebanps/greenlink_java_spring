package com.api.greenlink.exceptions.exception;

import com.api.greenlink.exceptions.BaseException;


public class NotFoundException extends BaseException {

    public NotFoundException(String message) {
        super(message);
    }
}
