package com.ecommerce.auth.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PermissionResponse {

    private UUID id;
    private String name;
    private String resource;
    private String action;
    private String description;

    @JsonProperty("created_at")
    private Instant createdAt;
}
