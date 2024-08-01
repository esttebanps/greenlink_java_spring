package com.api.greenlink.auth.service;

import javax.crypto.SecretKey;

public interface SecretKeyConverter {
    SecretKey getSecretKey(String secret);
}
