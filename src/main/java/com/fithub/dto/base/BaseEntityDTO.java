package com.fithub.dto.base;

import com.fithub.model.base.Company;
import com.fithub.model.user.User;
import lombok.Data;

@Data
public class BaseEntityDTO {
    private String createDate;
    private String updateDate;
    private User createdBy;
    private User updatedBy;
    private Company company;
}
