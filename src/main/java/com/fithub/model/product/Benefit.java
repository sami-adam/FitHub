package com.fithub.model.product;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "benefits")
@Data
public class Benefit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;

}
