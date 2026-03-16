package com.ecommerce.auth.service;

import com.ecommerce.auth.model.response.AuthResponse;
import com.ecommerce.auth.model.request.LoginRequest;
import com.ecommerce.auth.model.request.RegisterRequest;
import com.ecommerce.auth.model.response.UserResponse;
import com.ecommerce.auth.model.entity.Role;
import com.ecommerce.auth.model.entity.User;
import com.ecommerce.auth.exception.AuthenticationException;
import com.ecommerce.auth.exception.DuplicateResourceException;
import com.ecommerce.auth.exception.ResourceNotFoundException;
import com.ecommerce.auth.repository.RoleRepository;
import com.ecommerce.auth.repository.UserRepository;
import com.ecommerce.auth.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        log.info("Registering new user with email: {}", request.getEmail());

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Email already registered: " + request.getEmail());
        }

        // Get customer role (default for self-registration)
        Role customerRole = roleRepository.findByName("customer")
                .orElseThrow(() -> new ResourceNotFoundException("Customer role not found"));

        // Create user
        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .passwordHash(passwordEncoder.encode(request.getPassword() + "." + request.getEmail()))
                .role(customerRole)
                .active(true)
                .build();

        userRepository.save(user);
        log.info("User registered successfully with id: {}", user.getId());

        return generateAuthResponse(user);
    }

    @Transactional(readOnly = true)
    public AuthResponse login(LoginRequest request) {
        log.info("Login attempt for email: {}", request.getEmail());

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new AuthenticationException("Invalid email or password 1"));

        if (!user.getActive()) {
            throw new AuthenticationException("Account is deactivated");
        }

        if (!passwordEncoder.matches((request.getPassword() + "." + request.getEmail()), user.getPasswordHash())) {
            throw new AuthenticationException("Invalid email or password 2");
        }

        log.info("User logged in successfully: {}", user.getEmail());

        return generateAuthResponse(user);
    }

    @Transactional
    public void logout(String token) {
        jwtUtil.blacklistToken(token);
        log.info("User logged out successfully");
    }

    private AuthResponse generateAuthResponse(User user) {
        String accessToken = jwtUtil.generateAccessToken(user);
        String refreshToken = jwtUtil.generateRefreshToken(user);

        UserResponse userResponse = UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .roleName(user.getRole().getName())
                .permissions(user.getRole().getPermissions().stream()
                        .map(p -> p.getResource() + ":" + p.getAction())
                        .toList())
                .isActive(user.getActive())
                .updatedAt(user.getUpdatedAt() == null ? user.getCreatedAt() : user.getUpdatedAt())
                .build();

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .tokenType("Bearer")
                .expiresIn(jwtUtil.getExpiration())
                .user(userResponse)
                .build();
    }
}
