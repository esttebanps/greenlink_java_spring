package com.api.greenlink.auth.security.config;

import com.api.greenlink.auth.security.config.SecurityConfigurer;
import com.api.greenlink.auth.security.jwt.JwtFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class Config {

    private final SecurityConfigurer securityConfigurer;
    private final JwtFilter jwtFilter;

    public Config(SecurityConfigurer securityConfigurer, JwtFilter jwtFilter) {
        this.securityConfigurer = securityConfigurer;
        this.jwtFilter = jwtFilter;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);

        securityConfigurer.configure(http);

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
