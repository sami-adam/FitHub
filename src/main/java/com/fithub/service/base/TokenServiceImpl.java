package com.fithub.service.base;

import com.fithub.model.base.Token;
import com.fithub.repository.base.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {
    private final TokenRepository tokenRepository;

    @Override
    public Boolean isTokenValid(String token) {
        Token savedToken = tokenRepository.findByToken(token);
        if(savedToken == null) {
            return true;
        }
        return savedToken.getValid();
    }

    @Override
    public void invalidateToken(String token) {
        Token savedToken = tokenRepository.findByToken(token);
        if(savedToken != null) {
            savedToken.setValid(false);
            tokenRepository.save(savedToken);
        }
        Token invalidToken = new Token();
        invalidToken.setToken(token);
        invalidToken.setValid(false);
        tokenRepository.save(invalidToken);
    }
}
