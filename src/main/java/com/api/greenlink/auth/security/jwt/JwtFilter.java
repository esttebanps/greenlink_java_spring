package com.api.greenlink.auth.security.jwt;
import com.api.greenlink.auth.service.SecretKeyConverter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.api.greenlink.util.JwtUtil.getUsernameFromToken;
import static com.api.greenlink.util.JwtUtil.isTokenValid;


@Configuration
public class JwtFilter extends OncePerRequestFilter {

    private final String secret;
    private final UserDetailsService userDetailsService;
    private final SecretKeyConverter secretKeyConverter;
    public JwtFilter(UserDetailsService userDetailsService, @Value("${app.security.jwt.secret}") String jwtSecret, SecretKeyConverter secretKeyConverter) {
        this.userDetailsService = userDetailsService;
        this.secret = jwtSecret;
        this.secretKeyConverter = secretKeyConverter;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        String token = this.extractToken(request);

        if (isTokenValid(token, secretKeyConverter.getSecretKey(secret))){
            String username = getUsernameFromToken(token,secretKeyConverter.getSecretKey(secret));
            UserDetails user = userDetailsService.loadUserByUsername(username);
            Authentication auth = new UsernamePasswordAuthenticationToken(
                    user.getUsername(),
                    user.getPassword(),
                    user.getAuthorities()
            );
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        filterChain.doFilter(request, response);
    }

    public String extractToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasLength(bearerToken) && bearerToken.startsWith("Bearer")) {
            return bearerToken.substring("Bearer ".length());
        }
        return null;
    }
}
