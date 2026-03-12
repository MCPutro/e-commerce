package com.ecommerce.auth.controller;

import com.ecommerce.auth.model.request.UpdateProfileRequest;
import com.ecommerce.auth.model.response.ApiResponse;
import com.ecommerce.auth.model.request.CreateUserRequest;
import com.ecommerce.auth.model.response.PaginatedResponse;
import com.ecommerce.auth.model.response.UserResponse;
import com.ecommerce.auth.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@SecurityRequirement(name = "Authentication")
public class UserController {

    private final UserService userService;

    @GetMapping("/auth/me")
    public ResponseEntity<ApiResponse<UserResponse>> getCurrentUser(Authentication authentication) {
        String email = authentication.getName();
        UserResponse user = userService.getCurrentUser(email);
        return ResponseEntity.ok(ApiResponse.success(user));
    }

    @PutMapping("/auth/me")
    public ResponseEntity<ApiResponse<UserResponse>> updateProfile(
            Authentication authentication,
            @Valid @RequestBody UpdateProfileRequest request) {
        String email = authentication.getName();
        UserResponse user = userService.updateProfile(email, request);
        return ResponseEntity.ok(ApiResponse.success(user, "Profile updated successfully"));
    }

    @GetMapping("/users")
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity<ApiResponse<PaginatedResponse<UserResponse>>> getAllUsers(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int perPage,
            @RequestHeader(name = "withDetail", required = false, defaultValue = "false") boolean withDetail
    ) {
        PaginatedResponse<UserResponse> users = userService.getAllUsers(page, perPage, withDetail);
        return ResponseEntity.ok(ApiResponse.success(users));
    }

    @PostMapping("/users")
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity<ApiResponse<UserResponse>> createUser(
            @Valid @RequestBody CreateUserRequest request) {
        UserResponse user = userService.createUser(request);
        return ResponseEntity.ok(ApiResponse.success(user, "User created successfully"));
    }

    @GetMapping("/users/{id}")
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity<ApiResponse<UserResponse>> getUserById(@PathVariable UUID id) {
        UserResponse user = userService.getUserById(id);
        return ResponseEntity.ok(ApiResponse.success(user));
    }

    @PutMapping("/users/{id}")
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity<ApiResponse<UserResponse>> updateUser(
            @PathVariable UUID id,
            @Valid @RequestBody CreateUserRequest request) {
        UserResponse user = userService.updateUser(id, request);
        return ResponseEntity.ok(ApiResponse.success(user, "User updated successfully"));
    }

    @DeleteMapping("/users/{id}")
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable UUID id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(ApiResponse.success(null, "User deleted successfully"));
    }
}
