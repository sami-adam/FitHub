package com.fithub.service.fitnessClass;

import com.fithub.dto.fitnessClass.ClassEnrollmentDTO;
import com.fithub.model.fitnessClass.ClassEnrollment;
import com.fithub.model.fitnessClass.ClassSchedule;
import com.fithub.model.fitnessClass.FitnessClass;
import com.fithub.repository.fitnessClass.ClassEnrollmentRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ClassEnrollmentServiceImpl implements ClassEnrollmentService{
    private final ClassEnrollmentRepository classEnrollmentRepository;
    private final ModelMapper mapper = new ModelMapper();

    @Override
    public List<ClassEnrollmentDTO> getClassEnrollments() {
        return classEnrollmentRepository.findAll().stream()
                .map(classEnrollment -> mapper.map(classEnrollment, ClassEnrollmentDTO.class))
                .toList();
    }

    @Override
    public ClassEnrollmentDTO addClassEnrollment(ClassEnrollmentDTO classEnrollmentDTO) {
        ClassEnrollment classEnrollment = mapper.map(classEnrollmentDTO, ClassEnrollment.class);
        return mapper.map(classEnrollmentRepository.save(classEnrollment), ClassEnrollmentDTO.class);
    }

    @Override
    public ClassEnrollmentDTO updateClassEnrollment(Long id, ClassEnrollmentDTO classEnrollmentDTO) {
        ClassEnrollment classEnrollment = mapper.map(classEnrollmentDTO, ClassEnrollment.class);
        if(classEnrollmentDTO.getFitnessClass() != null) {
            classEnrollment.setFitnessClass(mapper.map(classEnrollmentDTO.getFitnessClass(), FitnessClass.class));
        }
        if(classEnrollmentDTO.getClassSchedule() != null) {
            classEnrollment.setClassSchedule(mapper.map(classEnrollmentDTO.getClassSchedule(), ClassSchedule.class));
        }
        if(classEnrollmentDTO.getStartDate() != null) {
            classEnrollment.setStartDate(classEnrollmentDTO.getStartDate());
        }
        if(classEnrollmentDTO.getEndDate() != null) {
            classEnrollment.setEndDate(classEnrollmentDTO.getEndDate());
        }
        if(classEnrollmentDTO.getDiscountAmount() != 0) {
            classEnrollment.setDiscountAmount(classEnrollmentDTO.getDiscountAmount());
        }
        if(classEnrollmentDTO.getStatus() != null) {
            classEnrollment.setStatus(classEnrollmentDTO.getStatus());
        }

        return mapper.map(classEnrollmentRepository.save(classEnrollment), ClassEnrollmentDTO.class);
    }

    @Override
    public Map<String, String> deleteClassEnrollment(Long id) {
        if(! classEnrollmentRepository.existsById(id)) {
            return Map.of("message", "Class enrollment not found");
        }
        classEnrollmentRepository.deleteById(id);
        return Map.of("message", "Class enrollment deleted successfully");
    }

    @Override
    public List<ClassEnrollmentDTO> searchClassEnrollments(String keyword) {
        return List.of();
    }
}
