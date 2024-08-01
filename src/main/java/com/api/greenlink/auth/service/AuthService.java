package com.api.greenlink.auth.service;

import com.api.greenlink.auth.dto.Login;
import com.api.greenlink.auth.dto.LoginResponse;

public interface AuthService {
    LoginResponse login(Login login);
}
