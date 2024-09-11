package com.fithub.model.employee;

import com.fithub.model.base.BaseEntity;
import com.fithub.model.user.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "employees")
@Data
public class Employee extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, name = "identification_number")
    private String identificationNumber;
    private String name;
    @Column(unique = true)
    private String email;
    @Column(unique = true)
    private String phone;
    private String address;

    @Enumerated(EnumType.STRING)
    @Column(name = "employee_type")
    private EmployeeType employeeType;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public enum EmployeeType {
        EMPLOYEE,
        INSTRUCTOR
    }
}
