package com.fithub.model.base;

import com.fithub.model.address.Address;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "companies")
@Data
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String company_name;
    @OneToOne
    @JoinColumn(name = "address_id")
    private Address address;
    @Email
    private String email;
    private String phone;
    private String logo;
}
