package com.fithub.service.user;

import com.fithub.dto.user.JWTAuthenticationResponse;
import com.fithub.dto.user.RefreshTokenDTO;
import com.fithub.dto.user.SignInDTO;
import com.fithub.dto.user.SignUpDTO;
import com.fithub.model.user.User;

import java.util.Map;

public interface AuthenticationService {
    User signUp(SignUpDTO signUpDTO);
    JWTAuthenticationResponse signIn(SignInDTO signInDTO);
    JWTAuthenticationResponse refreshToken(RefreshTokenDTO refreshTokenDTO);
    Map<String, String> deleteAccount(Long token);
}
