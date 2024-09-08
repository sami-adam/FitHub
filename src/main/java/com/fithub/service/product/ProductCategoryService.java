package com.fithub.service.product;
import com.fithub.dto.product.ProductCategoryDTO;
import com.fithub.dto.subscription.SubscriptionDTO;

import java.util.List;
import java.util.Map;

public interface ProductCategoryService {
    List<ProductCategoryDTO> getProductCategories();
    ProductCategoryDTO getProductCategory(Long id);
    ProductCategoryDTO addProductCategory(ProductCategoryDTO productCategoryDTO);
    ProductCategoryDTO updateProductCategory(Long id, ProductCategoryDTO productCategoryDTO);
    Map<String, String> deleteProductCategory(Long productCategoryId);
    List<ProductCategoryDTO> searchProductCategories(String keyword);
    List<SubscriptionDTO> getSubscriptionsByCategory(Long productCategoryId);
}
