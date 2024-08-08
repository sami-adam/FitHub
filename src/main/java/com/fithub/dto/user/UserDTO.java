package com.fithub.dto.user;

import com.fithub.dto.base.BaseEntityDTO;
import lombok.Data;

@Data
public class UserDTO extends BaseEntityDTO {
    private Long id;
    private String name;
    private String username;
    private String email;
    private String password;
    private String role;
}
