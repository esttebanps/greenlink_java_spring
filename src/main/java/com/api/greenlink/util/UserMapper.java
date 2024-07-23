package com.api.greenlink.util;

import com.api.greenlink.dto.LoginResponse;
import com.api.greenlink.dto.UserRegistration;
import com.api.greenlink.dto.UserResponse;
import com.api.greenlink.entity.UserGreenlink;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public static UserResponse toUserResponse(UserGreenlink user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setUsername(user.getUsername());
        userResponse.setEmail(user.getEmail());
        userResponse.setCreated_at(user.getCreated_at());
        return userResponse;
    }

    public static LoginResponse toLoginResponse(String token) {
        return new LoginResponse(token);
    }
}
