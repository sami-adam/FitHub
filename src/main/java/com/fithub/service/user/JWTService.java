package com.fithub.service.user;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;

public interface JWTService {
    String extractUsername(String token);
    String generateToken(UserDetails userDetails);
    Boolean isTokenValid(String token, UserDetails userDetails);
    String generateRefreshToken(Map<String, Object> extraClaims, UserDetails userDetails);
    void invalidateToken(String token);
}
