package com.api.greenlink.controller;

import com.api.greenlink.dto.user.Login;
import com.api.greenlink.dto.user.LoginResponse;
import com.api.greenlink.dto.user.UserRegistration;
import com.api.greenlink.dto.user.UserResponse;
import com.api.greenlink.service.UserDetailsServiceImpl;
import lombok.AllArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/v1/es")
public class UserController {

    private final UserDetailsServiceImpl userService;

    @PostMapping("/auth/user")
    public ResponseEntity<UserResponse> addUser(@RequestBody UserRegistration userRegistration) throws BadRequestException {
        UserResponse response = userService.addUser(userRegistration);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/auth/login")
    public ResponseEntity<LoginResponse> login(@RequestBody Login login) {
        LoginResponse response = userService.login(login);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return ResponseEntity.ok(userService.getUsers());
    }
}
