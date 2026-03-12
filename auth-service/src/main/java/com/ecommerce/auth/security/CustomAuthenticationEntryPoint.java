package com.ecommerce.auth.security;

import com.ecommerce.auth.exception.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        log.error("Authentication error: {}", authException.getMessage());

        // Check if there's an exception attribute set by the filter
        Exception exception = (Exception) request.getAttribute("AUTH_EXCEPTION");
        String message = "Unauthorized";

        if (exception != null) {
            log.error("Exception from filter: {}", exception.getMessage());
            message = exception.getMessage();
        } else if (authException != null) {
            message = authException.getMessage();
        }

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");

        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("status", "error");
        errorResponse.put("code", HttpServletResponse.SC_UNAUTHORIZED);
        errorResponse.put("message", message);
        errorResponse.put("timestamp", Instant.now().toString());

        objectMapper.writeValue(response.getWriter(), errorResponse);
    }
}
