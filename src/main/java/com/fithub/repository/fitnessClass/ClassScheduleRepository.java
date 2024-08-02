package com.fithub.repository.fitnessClass;

import com.fithub.model.fitnessClass.ClassSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassScheduleRepository extends JpaRepository<ClassSchedule, Long> {
    //Todo: Add custom queries here
}
