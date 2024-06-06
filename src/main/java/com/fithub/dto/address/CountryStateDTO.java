package com.fithub.dto.address;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
public class CountryStateDTO {
    private Long id;
    private String name;
    private CountryDTO country;
    private String code;
}
