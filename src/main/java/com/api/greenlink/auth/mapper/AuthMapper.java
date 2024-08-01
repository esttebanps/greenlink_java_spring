package com.api.greenlink.auth.mapper;

import com.api.greenlink.auth.dto.LoginResponse;
import org.springframework.stereotype.Component;

@Component
public class AuthMapper {
    public static LoginResponse toLoginResponse(String token) {
        return new LoginResponse(token);
    }
}
