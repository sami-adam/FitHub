package com.fithub.repository.product;

import com.fithub.model.product.Benefit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BenefitRepository extends JpaRepository<Benefit, Long> {
    List<Benefit> findByNameContainingIgnoreCase(String name);
}
