package com.fithub.service.product;
import com.fithub.dto.product.ProductDTO;
import com.fithub.dto.subscription.SubscriptionDTO;

import java.util.List;
import java.util.Map;

public interface ProductService {
    List<ProductDTO> getProducts();
    ProductDTO getProduct(Long id);
    ProductDTO addProduct(ProductDTO productDTO);
    ProductDTO updateProduct(Long id, ProductDTO productDTO);
    Map<String,String> deleteProduct(Long productId);
    List<ProductDTO> searchProducts(String keyword);
}
