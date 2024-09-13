package com.api.greenlink.auth.security.authorization;

import com.api.greenlink.auth.repository.AuthRepository;
import com.api.greenlink.auth.security.jwt.JwtFilter;
import com.api.greenlink.auth.service.SecretKeyConverter;
import com.api.greenlink.flowerpot.entity.Flowerpot;
import com.api.greenlink.flowerpot.repository.FlowerpotRepository;
import com.api.greenlink.user.entity.UserGreenlink;
import com.api.greenlink.util.JwtUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.function.Supplier;

@Service
public class AuthorizationManager {
    private final JwtFilter jwtFilter;
    private final SecretKeyConverter converterKey;
    private final AuthRepository authRepository;
    private final FlowerpotRepository flowerpotRepository;
    private final String secret;

    public AuthorizationManager(JwtFilter jwtFilter,
                                SecretKeyConverter converterKey,
                                AuthRepository authRepository,
                                FlowerpotRepository flowerpotRepository,
                                @Value("${app.security.jwt.secret}") String secret) {
        this.jwtFilter = jwtFilter;
        this.converterKey = converterKey;
        this.authRepository = authRepository;
        this.flowerpotRepository = flowerpotRepository;
        this.secret = secret;
    }

    public AuthorizationDecision checkUserId(Supplier<Authentication> authenticationSupplier, RequestAuthorizationContext requestAuthorizationContext) {
        String tokenRequest = jwtFilter.extractToken(requestAuthorizationContext.getRequest());

        SecretKey secretKey = converterKey.getSecretKey(secret);
        String tokenUsername = JwtUtil.getUsernameFromToken(tokenRequest, secretKey);

        Long id = Long.valueOf(requestAuthorizationContext.getVariables().get("id"));
        String usernamedb = authRepository.findById(id)
                .map(UserGreenlink::getUsername)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        boolean hasAccess = authenticationSupplier.get().getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))
                || tokenUsername.equals(usernamedb);

        return new AuthorizationDecision(hasAccess);
    }

    public static String getUserAuthentication(){
        return SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();
    }

    @Transactional
    public AuthorizationDecision canAccessFlowerpots(Supplier<Authentication> authenticationSupplier, RequestAuthorizationContext requestAuthorizationContext) {
        String tokenRequest = jwtFilter.extractToken(requestAuthorizationContext.getRequest());
        SecretKey secretKey = converterKey.getSecretKey(secret);
        String tokenUsername = JwtUtil.getUsernameFromToken(tokenRequest, secretKey);

        boolean hasAccess = authenticationSupplier.get().getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))
                || authRepository.findByUsername(tokenUsername)
                .map(user -> flowerpotRepository.countByUser(user) > 0)
                .orElse(false);

        return new AuthorizationDecision(hasAccess);
    }

    public AuthorizationDecision canAccessFlowerpotById(Supplier<Authentication> authenticationSupplier, RequestAuthorizationContext requestAuthorizationContext) {
        String tokenRequest = jwtFilter.extractToken(requestAuthorizationContext.getRequest());
        SecretKey secretKey = converterKey.getSecretKey(secret);
        String tokenUsername = JwtUtil.getUsernameFromToken(tokenRequest, secretKey);

        Long id = Long.valueOf(requestAuthorizationContext.getVariables().get("id"));
        UserDetails userFromDb = authRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        boolean hasAccess = authenticationSupplier.get().getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))
                || tokenUsername.equals(userFromDb.getUsername());

        return new AuthorizationDecision(hasAccess);
    }
}
