package com.fithub.dto.base;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fithub.model.base.Company;
import com.fithub.model.user.User;
import lombok.Data;

@Data
public class BaseEntityDTO {
    private String createDate;
    private String updateDate;
    @JsonIgnoreProperties({"password", "company", "authorities", "role", "enabled", "accountNonExpired", "accountNonLocked", "credentialsNonExpired"})
    private User createdBy;
    @JsonIgnoreProperties({"password", "company", "authorities", "role", "enabled", "accountNonExpired", "accountNonLocked", "credentialsNonExpired"})
    private User updatedBy;
    private Company company;
}
