package com.fithub.controller.user;

import com.fithub.dto.user.JWTAuthenticationResponse;
import com.fithub.dto.user.RefreshTokenDTO;
import com.fithub.dto.user.SignInDTO;
import com.fithub.dto.user.SignUpDTO;
import com.fithub.model.user.User;
import com.fithub.service.user.AuthenticationService;
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
