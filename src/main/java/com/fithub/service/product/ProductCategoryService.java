package com.fithub.service.product;
import com.fithub.dto.product.ProductCategoryDTO;
import java.util.List;
import java.util.Map;

public interface ProductCategoryService {
    List<ProductCategoryDTO> getProductCategories();
    ProductCategoryDTO addProductCategory(ProductCategoryDTO productCategoryDTO);
    ProductCategoryDTO updateProductCategory(Long id, ProductCategoryDTO productCategoryDTO);
    Map<String, String> deleteProductCategory(Long productCategoryId);
    List<ProductCategoryDTO> searchProductCategories(String keyword);
}
