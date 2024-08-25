package com.fithub.service.fitnessClass;

import com.fithub.dto.base.AttachmentDTO;
import com.fithub.dto.fitnessClass.FitnessClassDTO;
import com.fithub.exception.ResourceNotFoundException;
import com.fithub.model.base.Attachment;
import com.fithub.model.fitnessClass.FitnessClass;
import com.fithub.repository.base.AttachmentRepository;
import com.fithub.repository.fitnessClass.FitnessClassRepository;
import com.fithub.service.base.AttachmentService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class FitnessClassServiceImpl implements FitnessClassService{
    private final FitnessClassRepository fitnessClassRepository;
    private final AttachmentRepository attachmentRepository;
    private final ModelMapper mapper;

    public FitnessClassServiceImpl(FitnessClassRepository fitnessClassRepository, AttachmentRepository attachmentRepository) {
        this.fitnessClassRepository = fitnessClassRepository;
        this.attachmentRepository = attachmentRepository;
        this.mapper = new ModelMapper();
    }
    @Override
    public List<FitnessClassDTO> getFitnessClasses() {
        return fitnessClassRepository.findAll().stream()
                .map(fitnessClass -> mapper.map(fitnessClass, FitnessClassDTO.class))
                .toList();
    }

    @Override
    public FitnessClassDTO getFitnessClass(Long id) {
        FitnessClass fitnessClass = fitnessClassRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Fitness class not found with id: " + id)
        );
        return mapper.map(fitnessClass, FitnessClassDTO.class);
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
        if(fitnessClassDTO.getImages() != null) {
            List<Attachment> images = new ArrayList<>();
            fitnessClass.getImages().clear();
            for(AttachmentDTO attachmentDTO: fitnessClassDTO.getImages()){
                if(attachmentDTO.getId() != null){
                    Attachment attachment = attachmentRepository.findById(attachmentDTO.getId()).orElseThrow();
                    attachment.setFitnessClass(fitnessClass);
                    attachment.setUrl(attachmentDTO.getUrl());
                    images.add(attachmentRepository.save(attachment));
                } else {
                    Attachment attachment = attachmentRepository.save(mapper.map(attachmentDTO, Attachment.class));
                    attachment.setFitnessClass(fitnessClass);
                    images.add(attachmentRepository.save(attachment));
                }
                fitnessClass.setImages(images);
            }
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
