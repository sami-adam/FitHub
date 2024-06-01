package com.fithub.service.impl;

import com.fithub.dto.JWTAuthenticationResponse;
import com.fithub.dto.RefreshTokenDTO;
import com.fithub.dto.SignInDTO;
import com.fithub.dto.SignUpDTO;
import com.fithub.model.Role;
import com.fithub.model.User;
import com.fithub.repository.UserRepository;
import com.fithub.service.AuthenticationService;
import com.fithub.service.JWTService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;

    public User signUp(SignUpDTO signUpDTO){
        User user = new User();
        user.setEmail(signUpDTO.getEmail());
        user.setName(signUpDTO.getName());
        user.setUsername(signUpDTO.getUsername());
        user.setRole(Role.USER);
        user.setPassword(passwordEncoder.encode(signUpDTO.getPassword()));

        return userRepository.save(user);
    }

    public JWTAuthenticationResponse signIn(SignInDTO signInDTO){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signInDTO.getEmail(), signInDTO.getPassword())
        );

        var user = userRepository.findByEmail(signInDTO.getEmail()).orElseThrow(() -> new IllegalArgumentException("Invalid Username or Password"));
        var jwt = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);

        JWTAuthenticationResponse jwtAuthenticationResponse = new JWTAuthenticationResponse();
        jwtAuthenticationResponse.setToken(jwt);
        jwtAuthenticationResponse.setRefreshToken(refreshToken);


        return jwtAuthenticationResponse;

    }

    public JWTAuthenticationResponse refreshToken(RefreshTokenDTO refreshTokenDTO){
        String userEmail = jwtService.extractUsername(refreshTokenDTO.getToken());
        User user = userRepository.findByEmail(userEmail).orElseThrow(()-> new UsernameNotFoundException("User Not Found"));
        if(jwtService.isTokenValid(refreshTokenDTO.getToken(), user)){
            var jwt = jwtService.generateToken(user);

            JWTAuthenticationResponse jwtAuthenticationResponse = new JWTAuthenticationResponse();
            jwtAuthenticationResponse.setToken(jwt);
            jwtAuthenticationResponse.setRefreshToken(refreshTokenDTO.getToken());


            return jwtAuthenticationResponse;
        }
        return null;
    }

    // Todo: Complete Reset process
    public String resetPasswordToken(String email){
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
        var jwt = jwtService.generateToken(user);
        return jwt;
    }


}
