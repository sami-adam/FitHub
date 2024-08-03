package com.fithub.dto.member;

import com.fithub.dto.product.ProductCategoryDTO;
import com.fithub.dto.product.ProductDTO;
import com.fithub.model.member.Gender;
import lombok.Data;

import java.time.LocalDate;

@Data
public class MembershipDTO {
    private Long id;
    // Member Information
    private String firstName;
    private String lastName;
    private String identificationNumber;
    private Gender gender;
    private String email;
    private String phone;
    // Membership Information
    private ProductCategoryDTO productCategory;
    private ProductDTO product;
    private Integer quantity;
    private LocalDate startDate;
    private LocalDate endDate;

}
