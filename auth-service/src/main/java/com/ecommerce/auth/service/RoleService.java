package com.ecommerce.auth.service;

import com.ecommerce.auth.model.response.PermissionResponse;
import com.ecommerce.auth.model.response.RoleResponse;
import com.ecommerce.auth.model.entity.Permission;
import com.ecommerce.auth.model.entity.Role;
import com.ecommerce.auth.exception.DuplicateResourceException;
import com.ecommerce.auth.exception.ResourceNotFoundException;
import com.ecommerce.auth.repository.PermissionRepository;
import com.ecommerce.auth.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    @Transactional(readOnly = true)
    public List<RoleResponse> getAllRoles(boolean withDetailPermission) {
        List<Role> roles = roleRepository.findAll();
        return roles.stream().map(role -> mapToRoleResponse(role, withDetailPermission)).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public RoleResponse getRoleById(UUID id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with id: " + id));
        return mapToRoleResponse(role, true);
    }

    @Transactional(readOnly = true)
    public RoleResponse getRoleByName(String name) {
        Role role = roleRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found: " + name));
        return mapToRoleResponse(role, true);
    }

    @Transactional
    public RoleResponse createRole(String name, String description, List<String> permissionNames) {
        if (roleRepository.existsByName(name)) {
            throw new DuplicateResourceException("Role already exists: " + name);
        }

        List<Permission> permissions = permissionNames != null ?
                permissionNames.stream()
                        .map(permissionName -> {
                            String[] parts = permissionName.split(":");
                            String resource = parts[0];
                            String action = parts.length > 1 ? parts[1] : "*";
                            return permissionRepository.findByName(permissionName)
                                    .orElseThrow(() -> new ResourceNotFoundException(
                                            "Permission not found: " + permissionName));
                        })
                        .toList()
                : List.of();

        Role role = Role.builder()
                .name(name)
                .description(description)
                .permissions(new HashSet<>(permissions))
                .build();

        roleRepository.save(role);
        log.info("Role created successfully with id: {}", role.getId());

        return mapToRoleResponse(role, true);
    }

    @Transactional
    public RoleResponse updateRole(UUID id, String name, String description, List<String> permissionNames) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with id: " + id));

        if (name != null && !name.isBlank()) {
            if (!name.equals(role.getName()) && roleRepository.existsByName(name)) {
                throw new DuplicateResourceException("Role already exists: " + name);
            }
            role.setName(name);
        }

        if (description != null) {
            role.setDescription(description);
        }

        if (permissionNames != null) {
            List<Permission> permissions = permissionNames.stream()
                    .map(permissionName -> {
                        String[] parts = permissionName.split(":");
                        String resource = parts[0];
                        String action = parts.length > 1 ? parts[1] : "*";
                        return permissionRepository.findByName(permissionName)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                        "Permission not found: " + permissionName));
                    })
                    .collect(Collectors.toList());
            role.setPermissions(permissions.stream().collect(Collectors.toSet()));
        }

        roleRepository.save(role);
        log.info("Role updated successfully: {}", role.getId());

        return mapToRoleResponse(role, true);
    }

    @Transactional
    public void deleteRole(UUID id) {
        if (!roleRepository.existsById(id)) {
            throw new ResourceNotFoundException("Role not found with id: " + id);
        }
        roleRepository.deleteById(id);
        log.info("Role deleted successfully: {}", id);
    }

    private RoleResponse mapToRoleResponse(Role role, boolean withDetailPermission) {
//        List<PermissionResponse> permissionResponses = role.getPermissions().stream()
//                .map(permission -> PermissionResponse.builder()
//                        .id(permission.getId())
//                        .name(permission.getName())
//                        .resource(permission.getResource())
//                        .action(permission.getAction())
//                        .description(permission.getDescription())
//                        .createdAt(permission.getCreatedAt())
//                        .build())
//                .collect(Collectors.toList());

        return RoleResponse.builder()
                .id(role.getId())
                .name(role.getName())
                .description(role.getDescription())
                .permissions(withDetailPermission ?
                        role.getPermissions().stream()
                                .map(permission -> PermissionResponse.builder()
                                        .id(permission.getId())
                                        .name(permission.getName())
                                        .resource(permission.getResource())
                                        .action(permission.getAction())
                                        .description(permission.getDescription())
                                        .createdAt(permission.getCreatedAt())
                                        .build())
                                .collect(Collectors.toList())
                        : null)
                .createdAt(role.getCreatedAt())
                .build();
    }
}
