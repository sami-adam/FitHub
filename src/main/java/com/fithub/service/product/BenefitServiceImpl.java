package com.fithub.service.product;

import com.fithub.dto.product.BenefitDTO;
import com.fithub.model.product.Benefit;
import com.fithub.repository.product.BenefitRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BenefitServiceImpl implements BenefitService{
    private final BenefitRepository benefitRepository;
    private final ModelMapper mapper = new ModelMapper();

    @Override
    public List<BenefitDTO> getBenefits() {
        return benefitRepository.findAll().stream()
                .map(benefit -> mapper.map(benefit, BenefitDTO.class))
                .toList();
    }

    @Override
    public BenefitDTO addBenefit(BenefitDTO benefitDTO) {
        Benefit benefit = mapper.map(benefitDTO, Benefit.class);
        return mapper.map(benefitRepository.save(benefit), BenefitDTO.class);
    }

    @Override
    public BenefitDTO updateBenefit(Long id, BenefitDTO benefitDTO) {
        Benefit benefit = benefitRepository.findById(id).orElseThrow();
        if(benefitDTO.getName() != null) {
            benefit.setName(benefitDTO.getName());
        }
        if(benefitDTO.getDescription() != null) {
            benefit.setDescription(benefitDTO.getDescription());
        }
        return mapper.map(benefitRepository.save(benefit), BenefitDTO.class);
    }

    @Override
    public Map<String, String> deleteBenefit(Long id) {
        benefitRepository.deleteById(id);
        return Map.of("message", "Benefit deleted successfully", "status", "success");
    }

    @Override
    public List<BenefitDTO> searchBenefits(String keyword) {
        return List.of();
    }
}
