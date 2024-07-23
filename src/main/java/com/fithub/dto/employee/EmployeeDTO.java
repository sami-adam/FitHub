package com.fithub.dto.employee;

import com.fithub.model.user.User;
import lombok.Data;

@Data
public class EmployeeDTO {
    private Long id;
    private String identificationNumber;
    private String name;
    private String email;
    private String phone;
    private String address;
    private User user;
}
