package com.fithub.controller.fitnessClass;

import com.fithub.dto.fitnessClass.ClassEnrollmentDTO;
import com.fithub.service.fitnessClass.ClassEnrollmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin( origins = "*" )
@RequiredArgsConstructor
public class ClassEnrollmentController {
    private final ClassEnrollmentService classEnrollmentService;

    @GetMapping("/class-enrollments")
    public List<ClassEnrollmentDTO> getClassEnrollments() {
        return classEnrollmentService.getClassEnrollments();
    }

    @PostMapping("/class-enrollment")
    public ClassEnrollmentDTO addClassEnrollment(@RequestBody ClassEnrollmentDTO classEnrollmentDTO) {
        return classEnrollmentService.addClassEnrollment(classEnrollmentDTO);
    }

    @PutMapping("/class-enrollment/{id}")
    public ClassEnrollmentDTO updateClassEnrollment(@PathVariable Long id, @RequestBody ClassEnrollmentDTO classEnrollmentDTO) {
        return classEnrollmentService.updateClassEnrollment(id, classEnrollmentDTO);
    }

    @DeleteMapping("/class-enrollment/{id}")
    public void deleteClassEnrollment(@PathVariable Long id) {
        classEnrollmentService.deleteClassEnrollment(id);
    }

    @GetMapping("/class-enrollments/search/{keyword}")
    public List<ClassEnrollmentDTO> searchClassEnrollments(@PathVariable String keyword) {
        return classEnrollmentService.searchClassEnrollments(keyword);
    }
}
