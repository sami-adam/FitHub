package com.fithub.dto.address;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
public class AddressDTO {
    private Long id;
    private String zip;
    private String street;
    private CityDTO city;
    private CountryStateDTO state;
    private CountryDTO country;
}
