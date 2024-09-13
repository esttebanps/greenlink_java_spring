package com.api.greenlink.user.service;

import com.api.greenlink.user.dto.UserRegistration;
import com.api.greenlink.user.dto.UserResponse;
import com.api.greenlink.user.dto.UserUpdate;
import org.apache.coyote.BadRequestException;

import java.util.List;
import java.util.Map;

public interface UserService {
    UserResponse addUser(UserRegistration newUser) throws BadRequestException;
    Map<String, Object> getUsers(int page, int pageSize);
    UserResponse findById(Long id);
    UserResponse updateUser(UserUpdate newUser, Long id);
}
