package com.api.greenlink.config.error;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class InvalidCredentialsException extends RuntimeException{
    public InvalidCredentialsException(String invalidCredentials) {
        super(invalidCredentials);
    }
}
