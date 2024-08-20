package com.fithub.service.impl;

import com.fithub.dto.member.MemberDTO;
import com.fithub.dto.user.*;
import com.fithub.exception.LoginException;
import com.fithub.model.user.Role;
import com.fithub.model.user.User;
import com.fithub.repository.user.UserRepository;
import com.fithub.service.member.MemberService;
import com.fithub.service.user.AuthenticationService;
import com.fithub.service.user.JWTService;
import lombok.RequiredArgsConstructor;
import org.apache.xmlbeans.impl.xb.xsdschema.Attribute;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    private final MemberService memberService;
    private final ModelMapper mapper = new ModelMapper();

    public User signUp(SignUpDTO signUpDTO){
        User user = new User();
        user.setEmail(signUpDTO.getEmail());
        user.setName(signUpDTO.getName());
        user.setUsername(signUpDTO.getUsername());
        user.setRole(Role.USER);
        user.setPassword(passwordEncoder.encode(signUpDTO.getPassword()));

        User savedUser = userRepository.save(user);
        // Create Member
        MemberDTO existingMember = memberService.findMemberByEmail(signUpDTO.getEmail());
        if(existingMember == null) {
            MemberDTO memberDTO = new MemberDTO();
            memberDTO.setEmail(signUpDTO.getEmail());
            String firstName = signUpDTO.getName().split(" ").length > 0 ? signUpDTO.getName().split(" ")[0] : "";
            String lastName = signUpDTO.getName().split(" ").length > 1 ? signUpDTO.getName().split(" ")[1] : "";
            memberDTO.setFirstName(firstName);
            memberDTO.setLastName(lastName);
            memberDTO.setUser(savedUser);
            memberService.addMember(memberDTO);
        }

        return savedUser;
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

    public Map<String, String> deleteAccount(Long id){
        User user = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
        MemberDTO member = memberService.findMemberByEmail(user.getEmail());

        if(member != null){
            memberService.deleteMember(member.getId());
        }
        userRepository.delete(user);
        Map<String, String> response = new HashMap<>();
        response.put("message", "User Deleted Successfully");
        response.put("status", "success");
        return response;
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

    // Sing out method
    public void signOut(String token){
        jwtService.invalidateToken(token);
    }


}
