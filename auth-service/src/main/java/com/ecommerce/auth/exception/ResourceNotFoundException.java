package com.ecommerce.auth.exception;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String resource, String identifier) {
        super(String.format("%s not found with %s", resource, identifier));
    }
}
