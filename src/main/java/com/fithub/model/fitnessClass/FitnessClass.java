package com.fithub.model.fitnessClass;

import com.fithub.model.base.Attachment;
import com.fithub.model.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "fitness_classes")
@Data
public class FitnessClass extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(columnDefinition = "TEXT", length = 1024)
    private String description;

    @Enumerated(EnumType.STRING)
    private IntensityLevel intensityLevel;

    @OneToMany
    @JoinColumn(name = "fitness_class_id")
    private List<Attachment> images;

    public enum IntensityLevel {
        LOW, MEDIUM, HIGH
    }
}
