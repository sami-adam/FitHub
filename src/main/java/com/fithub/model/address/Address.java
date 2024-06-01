package com.fithub.model.address;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Locale;

@Entity
@Table(name = "addresses")
@Data
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String zip;
    private String street;
    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;
    @ManyToOne
    @JoinColumn(name = "state_id")
    private CountryState state;
    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;
}
