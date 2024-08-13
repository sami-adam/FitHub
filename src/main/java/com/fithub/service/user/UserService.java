package com.fithub.service.user;

import com.fithub.dto.user.UserDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService {
    UserDetailsService userDetailsService();
    List<UserDTO> getUsers();
    UserDTO getUserByToken(String token);
    UserDTO getUserByEmail(String email);
}
