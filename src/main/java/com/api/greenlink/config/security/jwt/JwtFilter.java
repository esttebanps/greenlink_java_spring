package com.api.greenlink.config.security.jwt;

import com.api.greenlink.entity.UserGreenlink;
import com.api.greenlink.service.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.api.greenlink.config.security.Config.toSecretKey;
import static com.esttebanps.JwtUtil.getUsernameFromToken;
import static com.esttebanps.JwtUtil.isTokenValid;


@Component
public class JwtFilter extends OncePerRequestFilter {

    private final String secret;
    private final UserDetailsServiceImpl userDetailsService;
    public JwtFilter(UserDetailsServiceImpl userDetailsService, @Value("${app.security.jwt.secret}") String jwtSecret) {
        this.userDetailsService = userDetailsService;
        this.secret = jwtSecret;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        String token = this.extractToken(request);

        if (isTokenValid(token, toSecretKey(secret))){
            String username = getUsernameFromToken(token,toSecretKey(secret));
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

    private String extractToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasLength(bearerToken) && bearerToken.startsWith("Bearer")) {
            return bearerToken.substring("Bearer ".length());
        }
        return null;
    }
}
