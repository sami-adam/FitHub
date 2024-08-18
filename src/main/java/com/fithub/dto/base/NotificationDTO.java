package com.fithub.dto.base;

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
    private UserDTO user;
}
