package com.fithub.model.base;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalTime;

@Entity
@Data
@Table(name = "tokens")
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;

    private String refreshToken;

    private String username;

    private LocalTime issuedAt = LocalTime.now();

    private LocalTime expiration;

    private Boolean valid = true;
}
