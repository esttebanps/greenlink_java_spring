package com.api.greenlink.auth.service.impl;

import com.api.greenlink.auth.service.SecretKeyConverter;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;



@Component
public class SecretKeySHA256 implements SecretKeyConverter {
    @Override
    public SecretKey getSecretKey(String secret) {
        return new SecretKeySpec(secret.getBytes(), "HmacSHA256");
    }
}
