package com.fithub.model.address;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "country_states")
@Data
public class CountryState {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;
    private String code;
}
