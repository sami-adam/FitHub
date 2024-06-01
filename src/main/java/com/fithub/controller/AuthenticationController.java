package com.fithub.controller;

import com.fithub.dto.JWTAuthenticationResponse;
import com.fithub.dto.RefreshTokenDTO;
import com.fithub.dto.SignInDTO;
import com.fithub.dto.SignUpDTO;
import com.fithub.model.User;
import com.fithub.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/signUp")
    public ResponseEntity<User> signUp(@RequestBody SignUpDTO signUpDTO){
        return ResponseEntity.ok(authenticationService.signUp(signUpDTO));
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/signIn")
    public ResponseEntity<JWTAuthenticationResponse> signIn(@RequestBody SignInDTO signInDTO){
        return ResponseEntity.ok(authenticationService.signIn(signInDTO));
    }

    @PostMapping("/refresh")
    public ResponseEntity<JWTAuthenticationResponse> refreshToken(@RequestBody RefreshTokenDTO refreshTokenDTO){
        return ResponseEntity.ok(authenticationService.refreshToken(refreshTokenDTO));
    }
}
