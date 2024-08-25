package com.fithub.service.fitnessClass;

import com.fithub.dto.fitnessClass.FitnessClassDTO;

import java.util.List;
import java.util.Map;

public interface FitnessClassService {
    List<FitnessClassDTO> getFitnessClasses();
    FitnessClassDTO getFitnessClass(Long id);
    FitnessClassDTO addFitnessClass(FitnessClassDTO fitnessClassDTO);
    FitnessClassDTO updateFitnessClass(Long id, FitnessClassDTO fitnessClassDTO);
    Map<String, String> deleteFitnessClass(Long id);
    List<FitnessClassDTO> searchFitnessClasses(String keyword);
}
