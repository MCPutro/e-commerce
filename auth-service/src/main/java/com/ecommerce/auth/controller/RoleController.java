package com.ecommerce.auth.controller;

import com.ecommerce.auth.model.response.ApiResponse;
import com.ecommerce.auth.model.response.RoleResponse;
import com.ecommerce.auth.service.RoleService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
@SecurityRequirement(name = "Authentication")
public class RoleController {

    private final RoleService roleService;

    @GetMapping
    @PreAuthorize("hasAuthority('role:write')")
    public ResponseEntity<ApiResponse<List<RoleResponse>>> getAllRoles(
            @RequestHeader(value = "withDetailPermission", required = false) boolean withDetailPermission
    ) {
        List<RoleResponse> roles = roleService.getAllRoles(withDetailPermission);
        return ResponseEntity.ok(ApiResponse.success(roles));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('role:write')")
    public ResponseEntity<ApiResponse<RoleResponse>> getRoleById(@PathVariable UUID id) {
        RoleResponse role = roleService.getRoleById(id);
        return ResponseEntity.ok(ApiResponse.success(role));
    }

    @GetMapping("/name/{name}")
    @PreAuthorize("hasAuthority('role:write')")
    public ResponseEntity<ApiResponse<RoleResponse>> getRoleByName(@PathVariable String name) {
        RoleResponse role = roleService.getRoleByName(name);
        return ResponseEntity.ok(ApiResponse.success(role));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('role:write')")
    public ResponseEntity<ApiResponse<RoleResponse>> createRole(
            @RequestBody Map<String, Object> request) {
        String name = (String) request.get("name");
        String description = (String) request.get("description");
        @SuppressWarnings("unchecked")
        List<String> permissions = (List<String>) request.get("permissions");
        
        RoleResponse role = roleService.createRole(name, description, permissions);
        return ResponseEntity.ok(ApiResponse.success(role, "Role created successfully"));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('role:write')")
    public ResponseEntity<ApiResponse<RoleResponse>> updateRole(
            @PathVariable UUID id,
            @RequestBody Map<String, Object> request) {
        String name = (String) request.get("name");
        String description = (String) request.get("description");
        @SuppressWarnings("unchecked")
        List<String> permissions = (List<String>) request.get("permissions");
        
        RoleResponse role = roleService.updateRole(id, name, description, permissions);
        return ResponseEntity.ok(ApiResponse.success(role, "Role updated successfully"));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('role:write')")
    public ResponseEntity<ApiResponse<Void>> deleteRole(@PathVariable UUID id) {
        roleService.deleteRole(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Role deleted successfully"));
    }
}
