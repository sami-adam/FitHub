package com.fithub.model.product;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "product_categories")
public class ProductCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;

    @ManyToMany
    @JoinTable(
        name = "product_category_benefits",
        joinColumns = @JoinColumn(name = "product_category_id"),
        inverseJoinColumns = @JoinColumn(name = "benefit_id")
    )
    private List<Benefit> benefits;
}
