package com.fithub.service.impl;

import com.fithub.dto.user.JWTAuthenticationResponse;
import com.fithub.dto.user.RefreshTokenDTO;
import com.fithub.dto.user.SignInDTO;
import com.fithub.dto.user.SignUpDTO;
import com.fithub.exception.LoginException;
import com.fithub.model.user.Role;
import com.fithub.model.user.User;
import com.fithub.repository.user.UserRepository;
import com.fithub.service.user.AuthenticationService;
import com.fithub.service.user.JWTService;
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
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(signInDTO.getEmail(), signInDTO.getPassword())
            );
        } catch (Exception e){
            throw new LoginException("Invalid Username or Password");
        }

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
