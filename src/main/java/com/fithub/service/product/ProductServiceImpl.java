package com.fithub.service.product;

import com.fithub.dto.product.ProductDTO;
import com.fithub.model.product.Product;
import com.fithub.repository.product.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService{
    private final ProductRepository productRepository;
    private final ModelMapper mapper;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
        this.mapper = new ModelMapper();
    }
    @Override
    public List<ProductDTO> getProducts() {
        return productRepository.findAll().stream().map(product -> mapper.map(product, ProductDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public ProductDTO addProduct(ProductDTO productDTO) {
        productDTO.setId(null);
        Product product = mapper.map(productDTO, Product.class);
        productRepository.save(product);
        return mapper.map(product, ProductDTO.class);
    }

    @Override
    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        Product product = productRepository.findById(id).orElseThrow();
        if(productDTO.getName() != null && !productDTO.getName().isEmpty()) {
            product.setName(productDTO.getName());
        }
        if(productDTO.getDescription() != null && !productDTO.getDescription().isEmpty()) {
            product.setDescription(productDTO.getDescription());
        }
        if(productDTO.getPrice() != null && productDTO.getPrice() > 0) {
            product.setPrice(productDTO.getPrice());
        }
        if(productDTO.getImage()!=null && !productDTO.getImage().isEmpty()) {
            product.setImage(productDTO.getImage());
        }
        productRepository.save(product);
        return mapper.map(product, ProductDTO.class);
    }

    @Override
    public Map<String, String> deleteProduct(Long productId) {
        productRepository.deleteById(productId);
        return Map.of("message", "Product deleted successfully", "status", "success");
    }
}
