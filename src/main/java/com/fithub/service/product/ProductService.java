package com.fithub.service.product;
import com.fithub.dto.product.ProductDTO;
import java.util.List;
import java.util.Map;

public interface ProductService {
    List<ProductDTO> getProducts();
    ProductDTO addProduct(ProductDTO productDTO);
    ProductDTO updateProduct(Long id, ProductDTO productDTO);
    Map<String,String> deleteProduct(Long productId);
}
