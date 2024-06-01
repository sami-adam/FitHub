package com.fithub.service;

import com.fithub.dto.JWTAuthenticationResponse;
import com.fithub.dto.RefreshTokenDTO;
import com.fithub.dto.SignInDTO;
import com.fithub.dto.SignUpDTO;
import com.fithub.model.User;

public interface AuthenticationService {
    User signUp(SignUpDTO signUpDTO);
    JWTAuthenticationResponse signIn(SignInDTO signInDTO);
    JWTAuthenticationResponse refreshToken(RefreshTokenDTO refreshTokenDTO);
}
