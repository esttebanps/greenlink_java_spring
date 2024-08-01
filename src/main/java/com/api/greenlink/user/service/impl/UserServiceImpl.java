package com.api.greenlink.user.service.impl;

import com.api.greenlink.auth.enums.Role;
import com.api.greenlink.auth.security.jwt.JwtFilter;
import com.api.greenlink.auth.service.SecretKeyConverter;
import com.api.greenlink.exceptions.exception.InvalidCredentialsException;
import com.api.greenlink.exceptions.exception.NotFoundException;
import com.api.greenlink.exceptions.exception.BadRequestException;
import com.api.greenlink.user.dto.UserRegistration;
import com.api.greenlink.user.dto.UserResponse;
import com.api.greenlink.user.entity.UserGreenlink;
import com.api.greenlink.user.mapper.UserMapper;
import com.api.greenlink.user.repository.UserRepository;
import com.api.greenlink.user.service.PasswordValidator;
import com.api.greenlink.user.service.UserService;
import com.api.greenlink.util.ResponseConvert;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.api.greenlink.user.mapper.UserMapper.toUserResponse;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncode;
    private final PasswordValidator passwordValidator;
    private final SecretKeyConverter secretKeyConverter;
    private final String secret;
    private final JwtFilter jwtFilter;
    private final ResponseConvert convert;

    public UserServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           PasswordValidator passwordValidator,
                           @Value("${app.security.jwt.secret}") String secret,
                           SecretKeyConverter secretKeyConverter,
                           JwtFilter jwtFilter, ResponseConvert convert
    ) {
        this.userRepository = userRepository;
        this.passwordEncode = passwordEncoder;
        this.passwordValidator = passwordValidator;
        this.secret = secret;
        this.secretKeyConverter = secretKeyConverter;
        this.jwtFilter = jwtFilter;
        this.convert = convert;
    }

    @Transactional
    public UserResponse addUser(UserRegistration newUser) {
        validateUserInput(newUser);
        validateUniqueConstraints(newUser);

        UserGreenlink userGreenlink = createUserGreenlink(newUser);
        userRepository.save(userGreenlink);

        return toUserResponse(userGreenlink);
    }

    private void validateUserInput(UserRegistration newUser) {
        if (newUser.getUsername() == null || newUser.getEmail() == null) {
            throw new BadRequestException("Username and Email must be provided");
        }

        if (!EmailConfirmation.isValidEmail(newUser.getEmail())) {
            throw new BadRequestException("Invalid email");
        }

        if (!passwordValidator.isValid(newUser.getPassword(), newUser.getConfirm_password())) {
            throw new BadRequestException("Password and confirmation do not match");
        }
    }

    private void validateUniqueConstraints(UserRegistration newUser) {
        if (userRepository.existsByUsername(newUser.getUsername())) {
            throw new BadRequestException("Username already exists");
        }

        if (userRepository.existsByEmail(newUser.getEmail())) {
            throw new BadRequestException("Email already exists");
        }
    }

    private UserGreenlink createUserGreenlink(UserRegistration newUser) {
        UserGreenlink userGreenlink = new UserGreenlink();
        userGreenlink.setUsername(newUser.getUsername());
        userGreenlink.setEmail(newUser.getEmail());
        userGreenlink.setPassword(passwordEncode.encode(newUser.getPassword()));
        userGreenlink.setAuthorities(newUser.getRoles() != null ? newUser.getRoles() : List.of(Role.ROLE_USER));
        return userGreenlink;
    }

    public Map<String, Object> getUsers(int page, int size){
        PageRequest pageable = PageRequest.of(page, size);
        Page<UserResponse> users = userRepository
                .findAll(pageable)
                .map(UserMapper::toUserResponse);

        if (users.isEmpty()) {
            throw new NotFoundException("No users found");
        }

        return convert.toResponse(page, size, users);
    }

    @Override
    public UserResponse findById(Long id) {

        Optional<UserGreenlink> user = userRepository.findById(id);

        if (user.isEmpty()) {
            throw new InvalidCredentialsException("Unauthorized");
        }

        return toUserResponse(user.get());

    }
}
