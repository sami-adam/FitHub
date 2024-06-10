package com.fithub.dto.user;

import lombok.Data;

@Data
public class JWTAuthenticationResponse {
    private String token;
    private String refreshToken;

}
