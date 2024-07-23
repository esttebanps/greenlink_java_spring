package com.api.greenlink.dto.user;

import com.api.greenlink.entity.enums.Role;
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
    private String confirm_password;
    private List<Role> roles;
}