package com.fithub.config;

import com.fithub.service.user.JWTService;
import com.fithub.service.user.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SecurityContextGenerator {
    private final  UserService usersService;
    private final JWTService jwtUtil;

    @Autowired
    public SecurityContextGenerator(UserService usersService, JWTService jwtUtil) {
        this.usersService = usersService;
        this.jwtUtil = jwtUtil;
    }

    @Value("${admin-email}")
    private String adminEmail;
    public SecurityContext createSecurityContext() {
        UserDetails userDetails = usersService.userDetailsService().loadUserByUsername(adminEmail); // Replace with actual username
        String jwtToken = jwtUtil.generateToken(userDetails);
        UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        org.springframework.security.core.context.SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(auth);
        SecurityContextHolder.setContext(context);
        return SecurityContextHolder.getContext();
    }
}
