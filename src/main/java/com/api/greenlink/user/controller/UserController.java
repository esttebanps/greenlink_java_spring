package com.api.greenlink.user.controller;

import com.api.greenlink.user.dto.UserRegistration;
import com.api.greenlink.user.dto.UserResponse;
import com.api.greenlink.user.service.UserService;
import lombok.AllArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/v1/es")
public class UserController {

    private final UserService userService;

    @PostMapping("/auth/user")
    public ResponseEntity<UserResponse> addUser(@RequestBody UserRegistration userRegistration) throws BadRequestException {
        UserResponse response = userService.addUser(userRegistration);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/users")
    public ResponseEntity<Map<String, Object>> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        return ResponseEntity.ok(userService.getUsers(page, size));
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id, Authentication authentication) {
        return ResponseEntity.ok(userService.findById(id));
    }
}
