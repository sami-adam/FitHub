package com.fithub.model.member;

import com.fithub.model.base.BaseEntity;
import com.fithub.model.product.Product;
import com.fithub.model.product.ProductCategory;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "memberships")
@Data
public class Membership extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Member Information
    private String firstName;
    private String lastName;
    private String identificationNumber;
    private Gender gender;
    private String email;
    private String phone;

    // Membership Information
    @ManyToOne
    @JoinColumn(name = "product_category_id")
    private ProductCategory productCategory;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private Integer quantity;
    private LocalDate startDate;
    private LocalDate endDate;


}
