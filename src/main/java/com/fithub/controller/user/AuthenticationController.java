package com.fithub.controller.user;

import com.fithub.dto.user.*;
import com.fithub.model.user.User;
import com.fithub.service.user.AuthenticationService;
import com.fithub.service.user.JWTService;
import com.fithub.service.user.UserService;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final JWTService jwtService;
    private final UserService userService;

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

    @GetMapping("/user")
    public ResponseEntity<UserDetails> getUser(@RequestHeader("Authorization") String token){
        String userEmail = jwtService.extractUsername(token.substring(7));
        UserDetails userDetails = userService.userDetailsService().loadUserByUsername(userEmail);
        return new ResponseEntity<>(userDetails, HttpStatus.OK);
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getUsers(){
        return ResponseEntity.ok(userService.getUsers());
    }
}
