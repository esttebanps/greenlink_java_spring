package com.api.greenlink.auth.service.impl;

import com.api.greenlink.auth.dto.Login;
import com.api.greenlink.auth.dto.LoginResponse;
import com.api.greenlink.auth.service.AuthService;
import com.api.greenlink.auth.service.TokenGenerator;
import com.api.greenlink.exceptions.exception.InvalidCredentialsException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.api.greenlink.auth.mapper.AuthMapper.toLoginResponse;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserDetailsServiceImpl userDetailsService;
    private final TokenGenerator tokenGenerator;
    private final PasswordEncoder passwordEncoder;

    public LoginResponse login(Login login){
        UserDetails userDetails = userDetailsService.loadUserByUsername(login.getUsername());

        if(userDetails == null ||!passwordEncoder.matches(login.getPassword(), userDetails.getPassword())){
            throw new InvalidCredentialsException("Password incorrect");
        }

        String username = userDetails.getUsername();
        String[] roles = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toArray(String[]::new);

        String token = tokenGenerator.createToken(username, roles);
        return toLoginResponse(token);
    }
}
