package com.api.greenlink.user.service.impl;


import com.api.greenlink.user.service.PasswordValidator;
import org.springframework.stereotype.Component;

@Component
public class PasswordConfirmation implements PasswordValidator {
    @Override
    public boolean isValid(String password, String passwordConfirmation) {
        return password != null && password.equals(passwordConfirmation);
    }
}
