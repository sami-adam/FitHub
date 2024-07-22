package com.fithub.controller.coach;

import com.fithub.dto.coach.CoachDTO;
import com.fithub.service.coach.CoachService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
public class CoachController {
    private final CoachService coachService;

    @Autowired
    public CoachController(CoachService coachService) {
        this.coachService = coachService;
    }

    @GetMapping("/coaches")
    public ResponseEntity<List<CoachDTO>> getCoaches() {
        return new ResponseEntity<>(coachService.getCoaches(), HttpStatus.OK);
    }

    @PostMapping("/coach")
    public ResponseEntity<CoachDTO> addCoach(@RequestBody CoachDTO coachDTO) {
        return new ResponseEntity<>(coachService.addCoach(coachDTO), HttpStatus.CREATED);
    }

    @PutMapping("/coach/{id}")
    public ResponseEntity<CoachDTO> updateCoach(@PathVariable Long id, @RequestBody CoachDTO coachDTO) {
        return new ResponseEntity<>(coachService.updateCoach(id, coachDTO), HttpStatus.OK);
    }

    @DeleteMapping("/coach/{id}")
    public ResponseEntity<?> deleteCoach(@PathVariable Long id) {
        return new ResponseEntity<>(coachService.deleteCoach(id), HttpStatus.OK);
    }

    @GetMapping("/coaches/search/{keyword}")
    public ResponseEntity<List<CoachDTO>> searchCoaches(@PathVariable String keyword) {
        return new ResponseEntity<>(coachService.searchCoaches(keyword), HttpStatus.OK);
    }

}
