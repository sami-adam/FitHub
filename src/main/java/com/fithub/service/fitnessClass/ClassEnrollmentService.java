package com.fithub.service.fitnessClass;

import com.fithub.dto.fitnessClass.ClassEnrollmentDTO;

import java.util.List;
import java.util.Map;

public interface ClassEnrollmentService {
    List<ClassEnrollmentDTO> getClassEnrollments();
    ClassEnrollmentDTO getClassEnrollment(Long id);
    ClassEnrollmentDTO addClassEnrollment(ClassEnrollmentDTO classEnrollmentDTO);
    ClassEnrollmentDTO updateClassEnrollment(Long id, ClassEnrollmentDTO classEnrollmentDTO);
    Map<String, String> deleteClassEnrollment(Long id);
    List<ClassEnrollmentDTO>  searchClassEnrollments(String keyword);
    List<ClassEnrollmentDTO> getMemberClassEnrollments(Long memberId);
}
