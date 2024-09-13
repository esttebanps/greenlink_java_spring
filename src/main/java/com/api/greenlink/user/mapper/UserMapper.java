package com.api.greenlink.user.mapper;

import com.api.greenlink.auth.dto.LoginResponse;
import com.api.greenlink.user.dto.UserResponse;
import com.api.greenlink.user.entity.UserGreenlink;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public static UserResponse toUserResponse(UserGreenlink user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setUsername(user.getUsername());
        userResponse.setEmail(user.getEmail());
        userResponse.setCreatedDate(user.getCreateDate());
        return userResponse;
    }
}
