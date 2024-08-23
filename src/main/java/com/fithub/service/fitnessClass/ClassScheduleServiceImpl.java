package com.fithub.service.fitnessClass;

import com.fithub.dto.fitnessClass.ClassScheduleDTO;
import com.fithub.exception.ResourceNotFoundException;
import com.fithub.model.employee.Employee;
import com.fithub.model.fitnessClass.ClassSchedule;
import com.fithub.model.fitnessClass.FitnessClass;
import com.fithub.repository.fitnessClass.ClassScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ClassScheduleServiceImpl implements ClassScheduleService {
    private final ClassScheduleRepository classScheduleRepository;
    private final ModelMapper mapper = new ModelMapper();
    @Override
    public List<ClassScheduleDTO> getFitnessClassSchedules() {
        return classScheduleRepository.findAll().stream().map(schedule -> mapper.map(schedule, ClassScheduleDTO.class)).toList();
    }

    @Override
    public ClassScheduleDTO addFitnessClassSchedule(ClassScheduleDTO classScheduleDTO) {
        ClassSchedule schedule = mapper.map(classScheduleDTO, ClassSchedule.class);
        return mapper.map(classScheduleRepository.save(schedule), ClassScheduleDTO.class);
    }

    // Update the fitness class schedule
    @Override
    public ClassScheduleDTO updateFitnessClassSchedule(Long id, ClassScheduleDTO classScheduleDTO) {
        System.out.println(classScheduleDTO);
        ClassSchedule schedule = classScheduleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Fitness Schedule not found with id: " + id));
        if(classScheduleDTO.getFitnessClass() != null) {
            schedule.setFitnessClass(mapper.map(classScheduleDTO.getFitnessClass(), FitnessClass.class));
        }
        if(classScheduleDTO.getInstructor() != null) {
            schedule.setInstructor(mapper.map(classScheduleDTO.getInstructor(), Employee.class));
        }
        if(classScheduleDTO.getPrice() != null) {
            schedule.setPrice(classScheduleDTO.getPrice());
        }
        if(classScheduleDTO.getStartDate() != null) {
            schedule.setStartDate(classScheduleDTO.getStartDate());
        }
        if(classScheduleDTO.getEndDate() != null) {
            schedule.setEndDate(classScheduleDTO.getEndDate());
        }
        if(classScheduleDTO.getStatus() != null) {
            schedule.setStatus(classScheduleDTO.getStatus());
        }

        return mapper.map(classScheduleRepository.save(schedule), ClassScheduleDTO.class);
    }

    @Override
    public Map<String, String> deleteFitnessClassSchedule(Long id) {
        if (!classScheduleRepository.existsById(id)) {
            throw new ResourceNotFoundException("Fitness Schedule not found with id: " + id);
        }
        classScheduleRepository.deleteById(id);
        return Map.of("message", "Fitness Schedule deleted successfully", "status", "success");
    }

    @Override
    public List<ClassScheduleDTO> searchFitnessClassSchedules(String keyword) {
        return List.of();
    }
}
