package com.fithub.service.fitnessClass;

import com.fithub.dto.fitnessClass.ClassScheduleDTO;

import java.util.List;
import java.util.Map;

public interface ClassScheduleService {
    List<ClassScheduleDTO> getFitnessClassSchedules();
    ClassScheduleDTO addFitnessClassSchedule(ClassScheduleDTO classScheduleDTO);
    ClassScheduleDTO updateFitnessClassSchedule(Long id, ClassScheduleDTO classScheduleDTO);
    Map<String, String> deleteFitnessClassSchedule(Long id);
    List<ClassScheduleDTO>  searchFitnessClassSchedules(String keyword);
}
