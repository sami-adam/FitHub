package com.fithub.dto.coach;

import com.fithub.model.user.User;
import lombok.Data;

@Data
public class CoachDTO {
    private Long id;
    private String identificationNumber;
    private String name;
    private String email;
    private String phone;
    private String address;
    private User user;
}
