package com.fithub.service.coach;

import com.fithub.dto.coach.CoachDTO;
import com.fithub.model.coach.Coach;
import com.fithub.repository.coach.CoachRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CoachServiceImpl implements CoachService{
    private final CoachRepository coachRepository;
    private final ModelMapper mapper;

    public CoachServiceImpl(CoachRepository coachRepository) {
        this.coachRepository = coachRepository;
        this.mapper = new ModelMapper();
    }

    @Override
    public List<CoachDTO> getCoaches() {
        return coachRepository.findAll().stream().map(coach -> mapper.map(coach, CoachDTO.class)).toList();
    }

    @Override
    public CoachDTO addCoach(CoachDTO coachDTO) {
        coachRepository.save(mapper.map(coachDTO, Coach.class));
        return coachDTO;
    }

    @Override
    public CoachDTO updateCoach(Long id, CoachDTO coachDTO) {
        Coach coach = coachRepository.findById(id).orElseThrow();
        if(coachDTO.getIdentificationNumber() != null) {
            coach.setIdentificationNumber(coachDTO.getIdentificationNumber());
        }
        if(coachDTO.getName() != null) {
            coach.setName(coachDTO.getName());
        }
        if(coachDTO.getEmail() != null) {
            coach.setEmail(coachDTO.getEmail());
        }
        if(coachDTO.getPhone() != null) {
            coach.setPhone(coachDTO.getPhone());
        }
        if(coachDTO.getAddress() != null) {
            coach.setAddress(coachDTO.getAddress());
        }
        if(coachDTO.getUser() != null) {
            coach.setUser(coachDTO.getUser());
        }
        coachRepository.save(coach);
        return mapper.map(coach, CoachDTO.class);
    }

    @Override
    public Map<String, String> deleteCoach(Long id) {
        coachRepository.deleteById(id);
        return Map.of("message", "Coach deleted successfully", "status", "success");
    }

    @Override
    public List<CoachDTO> searchCoaches(String keyword) {
        List<Coach> coaches = coachRepository.searchByIdentificationNumberContainingIgnoreCaseOrNameContainsIgnoreCaseOrEmailContaining(keyword, keyword, keyword);
        return coaches.stream().map(coach -> mapper.map(coach, CoachDTO.class)).toList();
    }
}
