package com.api.greenlink.auth.service.impl;

import com.api.greenlink.auth.service.SecretKeyConverter;
import com.api.greenlink.auth.service.TokenGenerator;
import com.esttebanps.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

@Component
public class TokenCreate implements TokenGenerator {

    private final String secret;
    private final SecretKeyConverter secretKeyConverter;

    public TokenCreate(@Value("${app.security.jwt.secret}") String jwtSecret, SecretKeyConverter secretKey) {
        this.secret = jwtSecret;
        this.secretKeyConverter = secretKey;
    }

    @Override
    public String createToken(String username, String[] roles) {
        SecretKey secretKey = secretKeyConverter.getSecretKey(secret);
        int durationDays = 2;
        return JwtUtil.createToken(username, roles, secretKey, durationDays);
    }

}
