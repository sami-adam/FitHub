package com.fithub.repository.fitnessClass;

import com.fithub.model.fitnessClass.FitnessClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FitnessClassRepository extends JpaRepository<FitnessClass, Long> {
    List<FitnessClass> searchByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String name, String description);
}
