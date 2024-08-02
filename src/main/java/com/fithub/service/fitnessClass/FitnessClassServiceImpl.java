package com.fithub.service.fitnessClass;

import com.fithub.dto.fitnessClass.FitnessClassDTO;
import com.fithub.model.fitnessClass.FitnessClass;
import com.fithub.repository.fitnessClass.FitnessClassRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class FitnessClassServiceImpl implements FitnessClassService{
    private final FitnessClassRepository fitnessClassRepository;
    private final ModelMapper mapper;

    public FitnessClassServiceImpl(FitnessClassRepository fitnessClassRepository) {
        this.fitnessClassRepository = fitnessClassRepository;
        this.mapper = new ModelMapper();
    }
    @Override
    public List<FitnessClassDTO> getFitnessClasses() {
        return fitnessClassRepository.findAll().stream()
                .map(fitnessClass -> mapper.map(fitnessClass, FitnessClassDTO.class))
                .toList();
    }

    @Override
    public FitnessClassDTO addFitnessClass(FitnessClassDTO fitnessClassDTO) {
        fitnessClassRepository.save(mapper.map(fitnessClassDTO, FitnessClass.class));
        return fitnessClassDTO;
    }

    @Override
    public FitnessClassDTO updateFitnessClass(Long id, FitnessClassDTO fitnessClassDTO) {
        FitnessClass fitnessClass = fitnessClassRepository.findById(id).orElseThrow();
        if(fitnessClassDTO.getName() != null && !fitnessClassDTO.getName().isEmpty()) {
            fitnessClass.setName(fitnessClassDTO.getName());
        }

        if(fitnessClassDTO.getDescription() != null && !fitnessClassDTO.getDescription().isEmpty()) {
            fitnessClass.setDescription(fitnessClassDTO.getDescription());
        }

        if(fitnessClassDTO.getIntensityLevel() != null && !fitnessClassDTO.getIntensityLevel().isEmpty()) {
            fitnessClass.setIntensityLevel(FitnessClass.IntensityLevel.valueOf(fitnessClassDTO.getIntensityLevel()));
        }
        if(fitnessClassDTO.getImages() != null && !fitnessClassDTO.getImages().isEmpty()) {
            fitnessClass.setImages(fitnessClassDTO.getImages());
        }

        fitnessClassRepository.save(fitnessClass);
        return mapper.map(fitnessClass, FitnessClassDTO.class);
    }

    @Override
    public Map<String, String> deleteFitnessClass(Long id) {
        fitnessClassRepository.deleteById(id);
        return Map.of("message", "Fitness class deleted successfully", "status", "success");
    }

    @Override
    public List<FitnessClassDTO> searchFitnessClasses(String keyword) {
        return fitnessClassRepository.searchByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(keyword, keyword).stream()
                .map(fitnessClass -> mapper.map(fitnessClass, FitnessClassDTO.class))
                .toList();
    }
}
