package com.fithub.repository.base;

import com.fithub.model.base.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
    Token findByToken(String token);
    Token findByRefreshToken(String refreshToken);
    List<Token> findByUsername(String username);
}
