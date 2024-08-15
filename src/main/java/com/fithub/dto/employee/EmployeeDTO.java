package com.fithub.dto.employee;

import com.fithub.dto.base.BaseEntityDTO;
import com.fithub.model.employee.Employee;
import com.fithub.model.user.User;
import lombok.Data;

@Data
public class EmployeeDTO extends BaseEntityDTO {
    private Long id;
    private String identificationNumber;
    private String name;
    private String email;
    private String phone;
    private String address;
    private Employee.EmployeeType employeeType;
    private User user;
}
