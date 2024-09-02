package com.fithub.service.user;

import com.fithub.dto.user.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService {
    UserDetailsService userDetailsService();
    Page<UserDTO> getUsers(Pageable pageable);
    UserDTO getUserByToken(String token);
    UserDTO getUserByEmail(String email);
    List<UserDTO> searchUsers(String keyword);
}
