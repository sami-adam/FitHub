package com.fithub.service.coach;

import com.fithub.dto.coach.CoachDTO;

import java.util.List;
import java.util.Map;

public interface CoachService {
    List<CoachDTO> getCoaches();
    CoachDTO addCoach(CoachDTO coachDTO);
    CoachDTO updateCoach(Long id, CoachDTO coachDTO);
    Map<String, String> deleteCoach(Long id);
    List<CoachDTO> searchCoaches(String keyword);
}
