package com.fithub.model.address;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "cities")
@Data
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    @ManyToOne
    @JoinColumn(name = "state_id")
    private CountryState state;
    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;

}
