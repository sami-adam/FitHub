package com.fithub.dto.product;

import com.fithub.dto.base.BaseEntityDTO;
import lombok.Data;

import java.util.Set;

@Data
public class ProductCategoryDTO {
    private Long id;
    private String name;
    private String description;

    private Set<BenefitDTO> benefits;
}
