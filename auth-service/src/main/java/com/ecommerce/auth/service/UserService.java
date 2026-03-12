package com.ecommerce.auth.service;

import com.ecommerce.auth.exception.AuthenticationException;
import com.ecommerce.auth.model.request.CreateUserRequest;
import com.ecommerce.auth.model.response.PaginatedResponse;
import com.ecommerce.auth.model.request.UpdateProfileRequest;
import com.ecommerce.auth.model.response.UserResponse;
import com.ecommerce.auth.model.entity.Role;
import com.ecommerce.auth.model.entity.User;
import com.ecommerce.auth.exception.DuplicateResourceException;
import com.ecommerce.auth.exception.ResourceNotFoundException;
import com.ecommerce.auth.repository.RoleRepository;
import com.ecommerce.auth.repository.UserRepository;
import com.ecommerce.auth.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    //    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new AuthenticationException("User not found with email: " + email));

        Set<GrantedAuthority> authorities = user.getRole().getPermissions().stream()
                .map(p -> new SimpleGrantedAuthority(p.getResource() + ":" + p.getAction()))
                .collect(Collectors.toSet());

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPasswordHash(),
                user.getIsActive(),
                true, true, true,
                authorities
        );
    }

    @Transactional(readOnly = true)
    public UserResponse getCurrentUser(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return mapToUserResponse(user, true);
    }

    @Transactional
    public UserResponse updateProfile(String email, UpdateProfileRequest request) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (request.getName() != null && !request.getName().isBlank()) {
            user.setName(request.getName());
        }

        if (request.getEmail() != null && !request.getEmail().isBlank()) {
            if (!request.getEmail().equals(user.getEmail()) &&
                    userRepository.existsByEmail(request.getEmail())) {
                throw new DuplicateResourceException("Email already in use");
            }
            user.setEmail(request.getEmail());
        }

//        if (request.getCurrentPassword() != null && request.getNewPassword() != null) {
//            if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPasswordHash())) {
//                throw new AuthenticationException("Current password is incorrect");
//            }
//            user.setPasswordHash(passwordEncoder.encode(request.getNewPassword()));
//        }

        userRepository.save(user);
        log.info("User profile updated: {}", user.getId());

        return mapToUserResponse(user, true);
    }

    @Transactional(readOnly = true)
    public PaginatedResponse<UserResponse> getAllUsers(int page, int perPage, boolean withDetail) {
        Pageable pageable = PageRequest.of(page - 1, perPage);
        Page<User> userPage = userRepository.findAll(pageable);

        List<UserResponse> users = userPage.getContent().stream()
                .map(user -> mapToUserResponse(user, withDetail))
                .toList();

//        List<UserResponse> users = userPage.getContent().stream()
//                .map(this::mapToUserResponse)
//                .collect(Collectors.toList());

        return PaginatedResponse.of(users, page, perPage, userPage.getTotalElements());
    }

    @Transactional
    public UserResponse createUser(CreateUserRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Email already registered: " + request.getEmail());
        }

        Role role = roleRepository.findById(request.getRoleId())
                .orElseThrow(() -> new ResourceNotFoundException("Role not found"));

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .passwordHash(request.getPassword())
                .role(role)
                .isActive(request.getIsActive() != null ? request.getIsActive() : true)
                .build();

        userRepository.save(user);
        log.info("User created successfully with id: {}", user.getId());

        return mapToUserResponse(user, true);
    }

    @Transactional(readOnly = true)
    public UserResponse getUserById(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        return mapToUserResponse(user, true);
    }

    @Transactional
    public UserResponse updateUser(UUID id, CreateUserRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        if (request.getName() != null && !request.getName().isBlank()) {
            user.setName(request.getName());
        }

        if (request.getEmail() != null && !request.getEmail().isBlank()) {
            if (!request.getEmail().equals(user.getEmail()) &&
                    userRepository.existsByEmail(request.getEmail())) {
                throw new DuplicateResourceException("Email already in use");
            }
            user.setEmail(request.getEmail());
        }

//        if (request.getPassword() != null && !request.getPassword().isBlank()) {
//            user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
//        }

        if (request.getRoleId() != null) {
            Role role = roleRepository.findById(request.getRoleId())
                    .orElseThrow(() -> new ResourceNotFoundException("Role not found"));
            user.setRole(role);
        }

        if (request.getIsActive() != null) {
            user.setIsActive(request.getIsActive());
        }

        userRepository.save(user);
        log.info("User updated successfully: {}", user.getId());

        return mapToUserResponse(user, true);
    }

    @Transactional
    public void deleteUser(UUID id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
        log.info("User deleted successfully: {}", id);
    }

    private UserResponse mapToUserResponse(User user, boolean withPermission) {
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .roleName(user.getRole().getName())
                .permissions(withPermission ?
                        user.getRole().getPermissions().stream()
                                .map(p -> p.getResource() + ":" + p.getAction())
                                .collect(Collectors.toList())
                        : null
                )
                .isActive(user.getIsActive())
                .createdAt(user.getCreatedAt())
                .build();
    }
}
