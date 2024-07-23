package com.api.greenlink.service;

import com.api.greenlink.config.error.InvalidCredentialsException;
import com.api.greenlink.config.error.NotFoundException;
import com.api.greenlink.dto.user.Login;
import com.api.greenlink.dto.user.LoginResponse;
import com.api.greenlink.dto.user.UserRegistration;
import com.api.greenlink.dto.user.UserResponse;
import com.api.greenlink.entity.UserGreenlink;
import com.api.greenlink.repository.UserRepository;
import com.api.greenlink.util.UserMapper;
import jakarta.transaction.Transactional;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

import java.util.List;

import static com.api.greenlink.config.security.Config.toSecretKey;
import static com.api.greenlink.util.UserMapper.toLoginResponse;
import static com.api.greenlink.util.UserMapper.toUserResponse;
import static com.esttebanps.JwtUtil.createToken;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final String secret;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncode;

    public UserDetailsServiceImpl(
            @Value("${app.security.jwt.secret}") String jwtSecret,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.secret = jwtSecret;
        this.userRepository = userRepository;
        this.passwordEncode = passwordEncoder;
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }


    @Transactional
    public UserResponse addUser(UserRegistration newUser) throws BadRequestException {
        if (!checkPassword(newUser.getPassword(), newUser.getConfirm_password())) {
            throw new InvalidCredentialsException("Password and confirmation do not match");
        }

        if (userRepository.existsByUsername(newUser.getUsername())) {
            throw new InvalidCredentialsException("Username already exists");
        }

        UserGreenlink userGreenlink = new UserGreenlink();
        userGreenlink.setUsername(newUser.getUsername());
        userGreenlink.setEmail(newUser.getEmail());
        userGreenlink.setPassword(passwordEncode.encode(newUser.getPassword()));
        userGreenlink.setAuthorities(newUser.getRoles());
        userRepository.save(userGreenlink);

        return toUserResponse(userGreenlink);
    }

    public List<UserResponse> getUsers(){

        List<UserResponse> users = userRepository
                .findAll()
                .stream()
                .map(UserMapper::toUserResponse)
                .toList();

        if (users.isEmpty()) {
            throw new NotFoundException();
        }

        return users;
    }

    public LoginResponse login(Login login){
        UserDetails userDetails = loadUserByUsername(login.getUsername());
        if(userDetails == null ||!passwordEncode.matches(login.getPassword(), userDetails.getPassword())){
            throw new InvalidCredentialsException();
        }
        String token = generateToken(userDetails);
        return toLoginResponse(token);
    }

    private String generateToken(UserDetails userDetails) {
        String username = userDetails.getUsername();
        String[] roles = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toArray(String[]::new);
        SecretKey secretKey = toSecretKey(secret);
        int durationDays = 2;
        return createToken(username, roles, secretKey, durationDays);
    }

    private boolean checkPassword(String password, String passwordConfirmation) {
        return password != null && password.equals(passwordConfirmation);
    }
}
