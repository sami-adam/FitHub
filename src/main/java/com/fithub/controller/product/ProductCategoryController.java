package com.fithub.controller.product;

import com.fithub.dto.product.ProductCategoryDTO;
import com.fithub.service.product.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
public class ProductCategoryController {
    private final ProductCategoryService productCategoryService;

    @Autowired
    public ProductCategoryController(ProductCategoryService productCategoryService) {
        this.productCategoryService = productCategoryService;
    }

    @GetMapping("/product-categories")
    public ResponseEntity<List<ProductCategoryDTO>> getProductCategories() {
        return new ResponseEntity<>(productCategoryService.getProductCategories(), HttpStatus.OK);
    }

    @GetMapping("/product-category/{id}")
    public ResponseEntity<ProductCategoryDTO> getProductCategory(@PathVariable Long id) {
        return new ResponseEntity<>(productCategoryService.getProductCategory(id), HttpStatus.OK);
    }

    @PostMapping("/product-category")
    public ResponseEntity<ProductCategoryDTO> addProductCategory(@RequestBody ProductCategoryDTO productCategoryDTO) {
        return new ResponseEntity<>(productCategoryService.addProductCategory(productCategoryDTO), HttpStatus.CREATED);
    }

    @PutMapping("/product-category/{id}")
    public ResponseEntity<ProductCategoryDTO> updateProductCategory(@PathVariable Long id, @RequestBody ProductCategoryDTO productCategoryDTO) {
        return new ResponseEntity<>(productCategoryService.updateProductCategory(id, productCategoryDTO), HttpStatus.OK);
    }

    @DeleteMapping("/product-category/{id}")
    public ResponseEntity<?> deleteProductCategory(@PathVariable Long id) {
        return new ResponseEntity<>(productCategoryService.deleteProductCategory(id), HttpStatus.OK);
    }

    @GetMapping("/product-categories/search/{keyword}")
    public ResponseEntity<List<ProductCategoryDTO>> searchProductCategories(@PathVariable String keyword) {
        return new ResponseEntity<>(productCategoryService.searchProductCategories(keyword), HttpStatus.OK);
    }
}
