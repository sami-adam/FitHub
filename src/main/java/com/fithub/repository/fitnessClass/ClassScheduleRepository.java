package com.fithub.repository.fitnessClass;

import com.fithub.model.fitnessClass.ClassSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassScheduleRepository extends JpaRepository<ClassSchedule, Long> {
    List<ClassSchedule> findByReferenceContainingIgnoreCase(String reference);
    @Query("SELECT s FROM ClassSchedule s WHERE s.fitnessClass.name LIKE %:keyword% OR s.instructor.name LIKE %:keyword%")
    List<ClassSchedule> searchFitnessClassSchedules(String keyword);
}
