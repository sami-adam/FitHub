package com.fithub.model.product;

import com.fithub.model.base.BaseEntity;
import com.fithub.model.base.DurationType;
import com.fithub.model.base.Tax;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
@Entity
@Table(name = "products")
public class Product extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT", length = 1024)
    private String image;
    private String name;
    private String description;
    private Double price;

    @Enumerated(EnumType.STRING)
    private DurationType durationType;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private ProductCategory category;

    @ManyToOne
    @JoinColumn(name = "tax_id")
    private Tax tax;
}
