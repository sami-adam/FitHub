package com.fithub.dto.address;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;


@Data
public class CityDTO {
    private Long id;
    private String name;
    private CountryStateDTO state;
    private CountryDTO country;

}
