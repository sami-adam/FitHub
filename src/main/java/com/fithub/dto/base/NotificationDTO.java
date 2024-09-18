package com.fithub.dto.base;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fithub.dto.user.UserDTO;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NotificationDTO {
    private Long id;
    private String reference;
    private String title;
    private String message;
    private LocalDateTime createdAt;
    private boolean read;
    @JsonIgnoreProperties({"company", "password", "role", "enabled"})
    private UserDTO user;
}
