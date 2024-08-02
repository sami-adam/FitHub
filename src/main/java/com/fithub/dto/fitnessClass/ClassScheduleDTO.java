package com.fithub.dto.fitnessClass;

import com.fithub.dto.employee.EmployeeDTO;
import com.fithub.model.fitnessClass.ClassSchedule;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ClassScheduleDTO {
    private Long id;
    private String reference;
    private FitnessClassDTO fitnessClass;
    private LocalDate startDate;
    private LocalDate endDate;
    private EmployeeDTO instructor;
    private ClassSchedule.Status status;
}
