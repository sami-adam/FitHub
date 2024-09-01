package com.fithub.controller.fitnessClass;

import com.fithub.dto.fitnessClass.ClassEnrollmentDTO;
import com.fithub.dto.fitnessClass.ClassScheduleDTO;
import com.fithub.dto.member.MemberDTO;
import com.fithub.dto.user.UserDTO;
import com.fithub.service.fitnessClass.ClassScheduleService;
import com.fithub.service.member.MemberService;
import com.fithub.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ClassScheduleController {
    private final ClassScheduleService classScheduleService;

    @GetMapping("/class-schedules")
    public ResponseEntity<List<ClassScheduleDTO>> getClassSchedules() {
        return new ResponseEntity<>(classScheduleService.getFitnessClassSchedules(), HttpStatus.OK);
    }

    @GetMapping("/class-schedule/{id}")
    public ResponseEntity<ClassScheduleDTO> getClassSchedule(@PathVariable Long id) {
        return new ResponseEntity<>(classScheduleService.getFitnessClassSchedule(id), HttpStatus.OK);
    }

    @PostMapping("/class-schedule")
    public ResponseEntity<ClassScheduleDTO> addClassSchedule(@RequestBody ClassScheduleDTO classScheduleDTO) {
        return new ResponseEntity<>(classScheduleService.addFitnessClassSchedule(classScheduleDTO), HttpStatus.CREATED);
    }

    @PutMapping("/class-schedule/{id}")
    public ResponseEntity<ClassScheduleDTO> updateClassSchedule(@PathVariable Long id, @RequestBody ClassScheduleDTO classScheduleDTO) {
        return new ResponseEntity<>(classScheduleService.updateFitnessClassSchedule(id, classScheduleDTO), HttpStatus.OK);
    }

    @DeleteMapping("/class-schedule/{id}")
    public ResponseEntity<?> deleteClassSchedule(@PathVariable Long id) {
        return new ResponseEntity<>(classScheduleService.deleteFitnessClassSchedule(id), HttpStatus.OK);
    }

    @GetMapping("/class-schedules/search/{keyword}")
    public ResponseEntity<List<ClassScheduleDTO>> searchClassSchedules(@PathVariable String keyword) {
        return new ResponseEntity<>(classScheduleService.searchFitnessClassSchedules(keyword), HttpStatus.OK);
    }

    @PostMapping("/class-schedule/enroll/{id}")
    public ResponseEntity<ClassEnrollmentDTO> enroll(@PathVariable Long id, @RequestParam(value = "memberId", required = false) Long memberId, @RequestHeader("Authorization") String token) {
        return new ResponseEntity<>(classScheduleService.enroll(id, memberId, token), HttpStatus.OK);
    }
}
