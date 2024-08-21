package com.fithub.service.impl;

import com.fithub.dto.user.UserDTO;
import com.fithub.repository.user.UserRepository;
import com.fithub.service.user.JWTService;
import com.fithub.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private ModelMapper mapper = new ModelMapper();
    private final JWTService jwtService;

    // User Details Service
    public UserDetailsService  userDetailsService(){
        return username -> userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    // Get Users
    public List<UserDTO> getUsers(){
        return userRepository.findAll().stream()
                .map(user -> mapper.map(user, UserDTO.class))
                .collect(Collectors.toList());
    }

    // Get User By Id
    public UserDTO getUserByToken(String token){
        String username = jwtService.extractUsername(token);
        return mapper.map(userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found")), UserDTO.class);
    }

    // Get User By Id
    public UserDTO getUserByEmail(String email){
        return mapper.map(userRepository.findByEmail(email) , UserDTO.class);
    }

    @Override
    public List<UserDTO> searchUsers(String keyword) {
        return userRepository.findByNameContainingIgnoreCaseOrUsernameContainingIgnoreCaseOrEmailContainingIgnoreCase(keyword, keyword, keyword).stream()
                .map(user -> mapper.map(user, UserDTO.class))
                .collect(Collectors.toList());
    }
}
