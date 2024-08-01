package com.api.greenlink.user.service;

public interface PasswordValidator {
    boolean isValid(String password, String passwordConfirmation);
}
