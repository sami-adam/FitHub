package com.fithub.dto.address;

import lombok.Data;

@Data
public class CountryStateDTO {
    private String name;
    private CountryDTO country;
    private String code;
}
