package com.fithub.controller.fitnessClass;

import com.fithub.dto.fitnessClass.ClassEnrollmentDTO;
import com.fithub.service.fitnessClass.ClassEnrollmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin( origins = "*" )
@RequiredArgsConstructor
public class ClassEnrollmentController {
    private final ClassEnrollmentService classEnrollmentService;

    @GetMapping("/class-enrollments")
    public ResponseEntity<List<ClassEnrollmentDTO>> getClassEnrollments() {
        return ResponseEntity.ok(classEnrollmentService.getClassEnrollments());
    }

    @GetMapping("/class-enrollment/{id}")
    public ResponseEntity<ClassEnrollmentDTO> getClassEnrollment(@PathVariable Long id) {
        return ResponseEntity.ok(classEnrollmentService.getClassEnrollment(id));
    }

    @PostMapping("/class-enrollment")
    public ResponseEntity<ClassEnrollmentDTO> addClassEnrollment(@RequestBody ClassEnrollmentDTO classEnrollmentDTO) {
        return ResponseEntity.ok(classEnrollmentService.addClassEnrollment(classEnrollmentDTO));
    }

    @PutMapping("/class-enrollment/{id}")
    public ResponseEntity<ClassEnrollmentDTO> updateClassEnrollment(@PathVariable Long id, @RequestBody ClassEnrollmentDTO classEnrollmentDTO) {
        return ResponseEntity.ok(classEnrollmentService.updateClassEnrollment(id, classEnrollmentDTO));
    }

    @DeleteMapping("/class-enrollment/{id}")
    public void deleteClassEnrollment(@PathVariable Long id) {
        classEnrollmentService.deleteClassEnrollment(id);
    }

    @GetMapping("/class-enrollments/search/{keyword}")
    public ResponseEntity<List<ClassEnrollmentDTO>> searchClassEnrollments(@PathVariable String keyword) {
        return ResponseEntity.ok(classEnrollmentService.searchClassEnrollments(keyword));
    }

    @GetMapping("/class-enrollments/member/{memberId}")
    public ResponseEntity<List<ClassEnrollmentDTO>> getMemberClassEnrollments(@PathVariable Long memberId) {
        return ResponseEntity.ok(classEnrollmentService.getMemberClassEnrollments(memberId));
    }
}
