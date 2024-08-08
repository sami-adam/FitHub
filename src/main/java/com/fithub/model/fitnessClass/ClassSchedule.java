package com.fithub.model.fitnessClass;

import com.fithub.model.base.BaseEntity;
import com.fithub.model.employee.Employee;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "class_schedules")
public class ClassSchedule extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String reference;
    @ManyToOne
    @JoinColumn(name = "fitness_class_id")
    private  FitnessClass fitnessClass;

    private LocalDate startDate;
    private LocalDate endDate;

    @ManyToOne
    @JoinColumn(name = "instructor_id")
    private Employee instructor;

    @Enumerated(EnumType.STRING)
    private Status status;

    public enum Status {
        NEW, PLANNED, RUNNING, FINISHED, CANCELLED
    }
}
