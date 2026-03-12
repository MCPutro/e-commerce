package com.ecommerce.auth.service;

import com.ecommerce.auth.model.entity.Permission;
import com.ecommerce.auth.model.response.PermissionResponse;
import com.ecommerce.auth.repository.PermissionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PermissionService {

    private final PermissionRepository permissionRepository;

    @Transactional(readOnly = true)
    public Map<String, List<PermissionResponse>> getAllPermission() {
        return this.permissionRepository.findAll().stream().map(this::mapToPermissionResponse)
                .collect(Collectors.groupingBy(
                        //key
                        PermissionResponse::getResource,
                        //value
                        Collectors.toList()
                ));
    }

    private PermissionResponse mapToPermissionResponse(Permission permission) {
        return PermissionResponse.builder()
                .id(permission.getId())
                .name(permission.getName())
                .resource(permission.getResource())
                .action(permission.getAction())
                .description(permission.getDescription())
                .createdAt(permission.getCreatedAt())
                .build();
    }
}
