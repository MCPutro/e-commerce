package com.ecommerce.auth.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoleResponse {

    private UUID id;
    private String name;
    private String description;

    @JsonProperty("permissions")
    private List<PermissionResponse> permissions;

    @JsonProperty("created_at")
    private Instant createdAt;
}
