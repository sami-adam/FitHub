package com.fithub.repository.coach;

import com.fithub.model.coach.Coach;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CoachRepository extends JpaRepository<Coach, Long> {
    List<Coach> searchByIdentificationNumberContainingIgnoreCaseOrNameContainsIgnoreCaseOrEmailContaining(String identificationNumber, String name, String email);
}
