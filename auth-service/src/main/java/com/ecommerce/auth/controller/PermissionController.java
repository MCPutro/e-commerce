package com.ecommerce.auth.controller;

import com.ecommerce.auth.model.response.ApiResponse;
import com.ecommerce.auth.model.response.PermissionResponse;
import com.ecommerce.auth.service.PermissionService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/permissions")
@RequiredArgsConstructor
@SecurityRequirement(name = "Authentication")
public class PermissionController {

    private final PermissionService permissionService;

    @GetMapping
    @PreAuthorize("hasAuthority('role:write')")
    public ResponseEntity<ApiResponse<Map<String, List<PermissionResponse>>>> getAllPermission() {
        Map<String, List<PermissionResponse>> allPermission = permissionService.getAllPermission();
        return ResponseEntity.ok(ApiResponse.success(allPermission));
    }
}
