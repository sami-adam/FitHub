package com.fithub.service.base;

public interface TokenService {
    Boolean isTokenValid(String token);
    void invalidateToken(String token);
}
