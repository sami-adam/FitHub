package com.fithub.dto.base;

import lombok.Data;

import java.time.LocalTime;

@Data
public class TokenDTO {
    private Long id;
    private String token;
    private String refreshToken;
    private String username;
    private LocalTime issuedAt = LocalTime.now();
    private LocalTime expiration;
    private Boolean valid = true;
}
