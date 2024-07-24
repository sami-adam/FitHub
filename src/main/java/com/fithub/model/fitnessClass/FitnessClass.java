package com.fithub.model.fitnessClass;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "fitness_classes")
@Data
public class FitnessClass {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(columnDefinition = "TEXT", length = 1024)
    private String description;

    @Enumerated(EnumType.STRING)
    private IntensityLevel intensityLevel;

    public enum IntensityLevel {
        LOW, MEDIUM, HIGH
    }
}
