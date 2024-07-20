package com.fithub.dto.base;

import lombok.Data;

@Data
public class TaxDTO {
    private Long id;
    private String name;
    private String code;
    private double rate;
    private boolean active;

}
