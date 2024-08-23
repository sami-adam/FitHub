package com.fithub.repository.fitnessClass;

import com.fithub.model.fitnessClass.ClassSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassScheduleRepository extends JpaRepository<ClassSchedule, Long> {
    List<ClassSchedule> findByReferenceContainingIgnoreCase(String reference);
}
