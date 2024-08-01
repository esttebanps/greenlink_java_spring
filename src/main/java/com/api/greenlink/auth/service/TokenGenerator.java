package com.api.greenlink.auth.service;

public interface TokenGenerator {
    String createToken(String username, String[] roles);
}
