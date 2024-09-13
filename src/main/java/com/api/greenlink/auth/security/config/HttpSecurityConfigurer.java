package com.api.greenlink.auth.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import com.api.greenlink.auth.security.authorization.AuthorizationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
class HttpSecurityConfigurer implements SecurityConfigurer {
    private final AuthorizationManager authorizationManager;

    public HttpSecurityConfigurer(AuthorizationManager authorizationManager) {
        this.authorizationManager = authorizationManager;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("v1/es/auth/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/v1/es/flowerpot").authenticated()
                .requestMatchers(HttpMethod.GET, "/v1/es/flowerpot/{id}").authenticated()
                .requestMatchers(HttpMethod.POST, "/v1/es/flowerpot").authenticated()
                .requestMatchers(HttpMethod.PUT, "/v1/es/flowerpot/{id}").authenticated()
                .requestMatchers(HttpMethod.DELETE, "/v1/es/flowerpot/{id}").authenticated()
                .requestMatchers(HttpMethod.POST, "/v1/es/flowerpot/{id}/data").authenticated()
                .requestMatchers(HttpMethod.GET, "/v1/es/flowerpot/{id}/data").authenticated()
                .requestMatchers(HttpMethod.GET, "v1/es/users").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/v1/es/users/{id}").authenticated()
                .requestMatchers(HttpMethod.GET, "v1/es/users/{id}")
                .access(authorizationManager::checkUserId)
                .anyRequest().authenticated()
        );

        http.httpBasic(Customizer.withDefaults());
    }
}