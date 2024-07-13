package com.fithub.service.product;

import com.fithub.dto.product.ProductCategoryDTO;
import com.fithub.model.product.ProductCategory;
import com.fithub.repository.product.ProductCategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService{
    private final ProductCategoryRepository productCategoryRepository;
    private final ModelMapper mapper;

    public ProductCategoryServiceImpl(ProductCategoryRepository productCategoryRepository) {
        this.productCategoryRepository = productCategoryRepository;
        this.mapper = new ModelMapper();
    }

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
        productCategoryRepository.save(productCategory);
        return mapper.map(productCategory, ProductCategoryDTO.class);
    }

    @Override
    public Map<String, String> deleteProductCategory(Long productCategoryId) {
        productCategoryRepository.deleteById(productCategoryId);
        return Map.of("message", "Product category deleted successfully", "status", "success");
    }
}
