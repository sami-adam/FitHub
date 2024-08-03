package com.fithub.service.product;

import com.fithub.dto.product.BenefitDTO;

import java.util.List;
import java.util.Map;

public interface BenefitService {
    List<BenefitDTO> getBenefits();
    BenefitDTO addBenefit(BenefitDTO benefitDTO);
    BenefitDTO updateBenefit(Long id, BenefitDTO benefitDTO);
    Map<String, String> deleteBenefit(Long id);
    List<BenefitDTO> searchBenefits(String keyword);
}
