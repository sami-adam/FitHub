package com.fithub.repository.fitnessClass;

import com.fithub.model.fitnessClass.FitnessClassSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FitnessClassScheduleRepository extends JpaRepository<FitnessClassSchedule, Long> {
    //Todo: Add custom queries here
}
