package com.fithub.repository.product;

import com.fithub.model.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT p FROM Product p WHERE p.name LIKE %?1%"
            + " OR p.description LIKE %?1%"
            + " OR p.category.name LIKE %?1%"
            + " OR p.category.description LIKE %?1%")
    List<Product> searchProducts(String keyword);
}
