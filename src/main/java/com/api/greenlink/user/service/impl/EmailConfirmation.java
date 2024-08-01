package com.api.greenlink.user.service.impl;

import com.api.greenlink.user.service.EmailValidator;

import java.util.regex.Pattern;

public class EmailConfirmation implements EmailValidator {
    static boolean isValidEmail(String email) {
        Pattern emailPattern = Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
        return emailPattern.matcher(email).matches();
    }
}
