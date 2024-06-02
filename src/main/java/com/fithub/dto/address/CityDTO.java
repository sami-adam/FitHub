package com.fithub.dto.address;
import lombok.Data;


@Data
public class CityDTO {

    private String name;
    private CountryStateDTO state;
    private CountryDTO country;

}
