package com.fithub.dto.fitnessClass;

import com.fithub.dto.employee.EmployeeDTO;
import com.fithub.model.fitnessClass.FitnessClassSchedule;
import lombok.Data;

import java.time.LocalDate;

@Data
public class FitnessClassScheduleDTO {
    private Long id;
    private String reference;
    private FitnessClassDTO fitnessClass;
    private LocalDate startDate;
    private LocalDate endDate;
    private EmployeeDTO instructor;
    private FitnessClassSchedule.Status status;
}
