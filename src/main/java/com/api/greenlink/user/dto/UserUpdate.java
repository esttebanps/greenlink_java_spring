package com.api.greenlink.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserUpdate {
    private String username;
    private String email;
    private String password;
    private String current_password;
    private String new_password;
    private String confirm_new_password;
    
}
