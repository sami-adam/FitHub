package com.fithub.dto.product;

import lombok.Data;

@Data
public class ProductDTO {
    private Long id;
    private String image;
    private String name;
    private String description;
    private Double price;
    private ProductCategoryDTO category;
}
