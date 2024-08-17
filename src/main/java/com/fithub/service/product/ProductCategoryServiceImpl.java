package com.fithub.service.product;

import com.fithub.dto.product.BenefitDTO;
import com.fithub.dto.product.ProductCategoryDTO;
import com.fithub.model.accounting.Account;
import com.fithub.model.product.Benefit;
import com.fithub.model.product.ProductCategory;
import com.fithub.repository.product.BenefitRepository;
import com.fithub.repository.product.ProductCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductCategoryServiceImpl implements ProductCategoryService{
    private final ProductCategoryRepository productCategoryRepository;
    private final BenefitRepository benefitRepository;
    private final ModelMapper mapper = new ModelMapper();


    @Override
    public List<ProductCategoryDTO> getProductCategories() {
        return productCategoryRepository.findAll().stream().map(productCategory -> mapper.map(productCategory, ProductCategoryDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public ProductCategoryDTO addProductCategory(ProductCategoryDTO productCategoryDTO) {
        productCategoryDTO.setId(null);
        ProductCategory productCategory = mapper.map(productCategoryDTO, ProductCategory.class);
        productCategoryRepository.save(productCategory);
        return mapper.map(productCategory, ProductCategoryDTO.class);
    }

    @Override
    public ProductCategoryDTO updateProductCategory(Long id, ProductCategoryDTO productCategoryDTO) {
        ProductCategory productCategory = productCategoryRepository.findById(id).orElseThrow();
        if(productCategoryDTO.getName() != null) {
            productCategory.setName(productCategoryDTO.getName());
        }
        if(productCategoryDTO.getDescription() != null) {
            productCategory.setDescription(productCategoryDTO.getDescription());
        }
        if(productCategoryDTO.getIncomeAccount() != null) {
            productCategory.setIncomeAccount(mapper.map(productCategoryDTO.getIncomeAccount(), Account.class));
        }
        if(productCategoryDTO.getExpenseAccount() != null) {
            productCategory.setExpenseAccount(mapper.map(productCategoryDTO.getExpenseAccount(), Account.class));
        }
        if(productCategoryDTO.getBenefits() != null) {
            Set<Benefit> benefits = new HashSet<>();
            for(BenefitDTO benefitDTO : productCategoryDTO.getBenefits()) {
                Benefit benefit = benefitRepository.findById(benefitDTO.getId()).orElseThrow();
                benefits.add(benefit);
            }
            productCategory.getBenefits().clear();
            productCategory.setBenefits(benefits);
        }
        productCategoryRepository.save(productCategory);

        return mapper.map(productCategory, ProductCategoryDTO.class);
    }

    @Override
    public Map<String, String> deleteProductCategory(Long productCategoryId) {
        productCategoryRepository.deleteById(productCategoryId);
        return Map.of("message", "Product category deleted successfully", "status", "success");
    }

    @Override
    public List<ProductCategoryDTO> searchProductCategories(String keyword) {
        return productCategoryRepository.findByNameContainingIgnoreCase(keyword).stream()
                .map(productCategory -> mapper.map(productCategory, ProductCategoryDTO.class))
                .collect(Collectors.toList());
    }
}
