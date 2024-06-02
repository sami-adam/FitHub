package com.fithub.dto.address;
import lombok.Data;

@Data
public class AddressDTO {
    private String zip;
    private String street;
    private CityDTO city;
    private CountryStateDTO state;
    private CountryDTO country;
}
