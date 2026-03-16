package com.ecommerce.auth.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    private UUID id;
    private String email;
    private String name;

    @JsonProperty("role_name")
    private String roleName;

    @JsonProperty("permissions")
    private List<String> permissions;

    @JsonProperty("is_active")
    private Boolean isActive;

    @JsonProperty("updated_at")
    private Instant updatedAt;
}
