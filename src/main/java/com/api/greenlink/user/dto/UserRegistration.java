package com.api.greenlink.user.dto;

import com.api.greenlink.auth.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserRegistration {
    private String username;
    private String email;
    private String password;
    private String confirmPassword;
    private List<Role> roles;
}
