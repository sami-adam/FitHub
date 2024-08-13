package com.fithub.dto.product;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fithub.dto.base.BaseEntityDTO;
import com.fithub.dto.base.TaxDTO;
import com.fithub.model.base.DurationType;
import lombok.Data;

@Data
public class ProductDTO extends BaseEntityDTO {
    private Long id;
    private String image;
    private String name;
    private String description;
    private Double price;
    @JsonIgnoreProperties({"benefits", "incomeAccount", "expenseAccount"})
    private ProductCategoryDTO category;
    private TaxDTO tax;
    private DurationType durationType;
}
