package com.fithub.dto.product;

import com.fithub.dto.base.BaseEntityDTO;
import lombok.Data;

import java.util.List;

@Data
public class ProductCategoryDTO extends BaseEntityDTO {
    private Long id;
    private String name;
    private String description;

    private List<BenefitDTO> benefits;
}
