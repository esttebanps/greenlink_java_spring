package com.api.greenlink.auth.security.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

interface SecurityConfigurer {
    void configure(HttpSecurity http) throws Exception;
}
