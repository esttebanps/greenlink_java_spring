package com.api.greenlink.exceptions.exception;


import com.api.greenlink.exceptions.BaseException;


public class BadRequestException extends BaseException {

    public BadRequestException(String message) {
        super(message);
    }

}