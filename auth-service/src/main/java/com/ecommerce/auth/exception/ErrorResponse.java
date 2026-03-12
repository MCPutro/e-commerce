package com.ecommerce.auth.exception;

import lombok.*;

import java.time.Instant;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {

    private String status;
    private int code;
    private String message;
    private List<FieldError> errors;
    private Instant timestamp;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FieldError {
        private String field;
        private String reason;
    }

    public static ErrorResponse of(String message, int code) {
        return ErrorResponse.builder()
                .status("error")
                .code(code)
                .message(message)
                .timestamp(Instant.now())
                .build();
    }

    public static ErrorResponse withFieldErrors(String message, int code, List<FieldError> fieldErrors) {
        return ErrorResponse.builder()
                .status("error")
                .code(code)
                .message(message)
                .errors(fieldErrors)
                .timestamp(Instant.now())
                .build();
    }
}
