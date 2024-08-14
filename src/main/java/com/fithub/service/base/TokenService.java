package com.fithub.service.base;

public interface TokenService {
    Boolean isTokenValid(String token);
    Void invalidateToken(String token);
}
