package com.ecommerce.auth.security;

import com.ecommerce.auth.model.entity.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Component
@Slf4j
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long expiration;

    @Value("${jwt.refresh-expiration}")
    private long refreshExpiration;

    // Token blacklist for logout
    private final Set<String> blacklistedTokens = ConcurrentHashMap.newKeySet();

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
//        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateAccessToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getId().toString());
        claims.put("email", user.getEmail());
        claims.put("role", user.getRole().getName());
        claims.put("permissions", user.getRole().getPermissions().stream()
                .map(p -> p.getResource() + ":" + p.getAction())
                .collect(Collectors.toList()));

        Instant now = Instant.now();
        Instant expiryInstant = now.plusMillis(expiration);
        Date expired = Date.from(expiryInstant);

        return Jwts.builder()
                .claims(claims)
                .subject(user.getEmail())
                .id(UUID.randomUUID().toString())
                .issuedAt(new Date())
                .expiration(expired)
                .signWith(getSigningKey())
                .compact();
    }

    public String generateRefreshToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getId().toString());
        claims.put("type", "refresh");

        Instant now = Instant.now();
        Instant expiryInstant = now.plusMillis(refreshExpiration);
        Date expired = Date.from(expiryInstant);

        return Jwts.builder()
                .claims(claims)
                .subject(user.getEmail())
                .id(UUID.randomUUID().toString())
                .issuedAt(new Date())
                .expiration(expired)
                .signWith(getSigningKey())
                .compact();
    }

    public Claims extractClaims(String token) {
        if (isBlacklisted(token)) {
            throw new JwtException("Token is blacklisted");
        }
        return Jwts.parser()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String extractEmail(String token) {
        return extractClaims(token).getSubject();
    }

    public UUID extractUserId(String token) {
        String userId = extractClaims(token).get("userId", String.class);
        return UUID.fromString(userId);
    }

    public String extractRole(String token) {
        return extractClaims(token).get("role", String.class);
    }

    @SuppressWarnings("unchecked")
    public List<String> extractPermissions(String token) {
        return extractClaims(token).get("permissions", List.class);
    }

//    public List<GrantedAuthority> extractAuthorities(String token) {
//        List<String> permissions = extractPermissions(token);
//        return permissions.stream()
//                .map(SimpleGrantedAuthority::new)
//                .collect(Collectors.toList());
//    }

    public boolean isTokenValid(String token, String email) {
        if (isBlacklisted(token)) {
            return false;
        }
        try {
            final String extractedEmail = extractEmail(token);
            return extractedEmail.equals(email) && !isTokenExpired(token);
        } catch (JwtException e) {
            log.error("Token validation error: {}", e.getMessage());
            return false;
        }
    }

    public boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }

    public void blacklistToken(String token) {
        blacklistedTokens.add(token);
        log.debug("Token blacklisted: {}", token);
    }

    public boolean isBlacklisted(String token) {
        return blacklistedTokens.contains(token);
    }

    public long getExpiration() {
        return expiration;
    }
}
