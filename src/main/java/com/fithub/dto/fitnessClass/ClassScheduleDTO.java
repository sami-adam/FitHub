package com.fithub.dto.fitnessClass;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fithub.dto.base.BaseEntityDTO;
import com.fithub.dto.employee.EmployeeDTO;
import com.fithub.model.fitnessClass.ClassSchedule;
import lombok.Data;

import java.util.Date;

@Data
public class ClassScheduleDTO extends BaseEntityDTO {
    private Long id;
    private String reference;
    @JsonIgnoreProperties({"images", "createdBy", "updatedBy"})
    private FitnessClassDTO fitnessClass;
    private Date startDate;
    private Date endDate;
    private EmployeeDTO instructor;
    private Double price;
    private ClassSchedule.Status status;
}
