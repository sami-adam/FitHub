package com.fithub.controller.user;

import com.fithub.dto.user.*;
import com.fithub.model.base.ResponseModel;
import com.fithub.model.user.User;
import com.fithub.service.user.AuthenticationService;
import com.fithub.service.user.JWTService;
import com.fithub.service.user.UserService;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final JWTService jwtService;
    private final UserService userService;

    @PostMapping("/v1/auth/signUp")
    public ResponseEntity<ResponseModel<User>> signUp(@RequestBody SignUpDTO signUpDTO){
        return ResponseEntity.ok(
                new ResponseModel<>(
                        true,
                        authenticationService.signUp(signUpDTO),
                        "User signed up successfully"
                )
        );
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/v1/auth/signIn")
    public ResponseEntity<ResponseModel<JWTAuthenticationResponse>> signIn(@RequestBody SignInDTO signInDTO){
        return ResponseEntity.ok(
                new ResponseModel<>(
                        true,
                        authenticationService.signIn(signInDTO),
                        "User signed in successfully"
                )
        );
    }

    @GetMapping("/v1/auth/signOut")
    public ResponseEntity<ResponseModel<String>> signOut(@RequestHeader("Authorization") String token){
        jwtService.invalidateToken(token.substring(7));
        return ResponseEntity.ok(
                new ResponseModel<>(
                        true,
                        null,
                        "User signed out successfully"
                )
        );
    }

    @PostMapping("/v1/auth/refresh")
    public ResponseEntity<JWTAuthenticationResponse> refreshToken(@RequestBody RefreshTokenDTO refreshTokenDTO){
        return ResponseEntity.ok(authenticationService.refreshToken(refreshTokenDTO));
    }

    @GetMapping("/v1/auth/user")
    public ResponseEntity<UserDetails> getUser(@RequestHeader("Authorization") String token){
        String userEmail = jwtService.extractUsername(token.substring(7));
        UserDetails userDetails = userService.userDetailsService().loadUserByUsername(userEmail);
        return new ResponseEntity<>(userDetails, HttpStatus.OK);
    }

    @DeleteMapping("/v1/auth/user/{id}")
    public ResponseEntity<Map<String, String>> deleteUser(@PathVariable("id") Long id){
        return ResponseEntity.ok(authenticationService.deleteAccount(id));
    }

    @GetMapping("/v1/auth/users")
    public ResponseEntity<List<UserDTO>> getUsers(Pageable pageable){
        return ResponseEntity.ok(userService.getUsers(pageable).getContent());
    }

    @GetMapping("/v1/auth/users/search/{keyword}")
    public ResponseEntity<List<UserDTO>> searchUsers(@PathVariable("keyword") String keyword){
        return ResponseEntity.ok(userService.searchUsers(keyword));
    }
}
