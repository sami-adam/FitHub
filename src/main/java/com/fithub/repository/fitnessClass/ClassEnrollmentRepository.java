package com.fithub.repository.fitnessClass;

import com.fithub.model.fitnessClass.ClassEnrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassEnrollmentRepository extends JpaRepository<ClassEnrollment, Long> {
}
