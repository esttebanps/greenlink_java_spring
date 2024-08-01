package com.api.greenlink.auth.controller;

import com.api.greenlink.auth.dto.Login;
import com.api.greenlink.auth.dto.LoginResponse;
import com.api.greenlink.auth.service.AuthService;
import com.api.greenlink.auth.service.impl.AuthServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/v1/es")
public class AuthController {

    private final AuthService userService;

    @PostMapping("/auth/login")
    public ResponseEntity<LoginResponse> login(@RequestBody Login login) {
        LoginResponse response = userService.login(login);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
