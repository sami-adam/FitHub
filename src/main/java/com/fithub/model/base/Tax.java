package com.fithub.model.base;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "taxes")
@Data
public class Tax {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String code;
    private double rate;
    private boolean active;

}
