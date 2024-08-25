package com.fithub.controller.fitnessClass;


import com.fithub.dto.fitnessClass.FitnessClassDTO;
import com.fithub.service.fitnessClass.FitnessClassService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
public class FitnessClassController {
    private final FitnessClassService fitnessClassService;

    public FitnessClassController(FitnessClassService fitnessClassService) {
        this.fitnessClassService = fitnessClassService;
    }

    @GetMapping("/fitness-classes")
    public ResponseEntity<List<FitnessClassDTO>> getFitnessClasses() {
        return new ResponseEntity<>(fitnessClassService.getFitnessClasses(), HttpStatus.OK);
    }

    @GetMapping("/fitness-class/{id}")
    public ResponseEntity<FitnessClassDTO> getFitnessClass(@PathVariable Long id) {
        return new ResponseEntity<>(fitnessClassService.getFitnessClass(id), HttpStatus.OK);
    }

    @PostMapping("/fitness-class")
    public ResponseEntity<FitnessClassDTO> addFitnessClass(@RequestBody FitnessClassDTO fitnessClassDTO) {
        return new ResponseEntity<>(fitnessClassService.addFitnessClass(fitnessClassDTO), HttpStatus.CREATED);
    }

    @PutMapping("/fitness-class/{id}")
    public ResponseEntity<FitnessClassDTO> updateFitnessClass(@PathVariable Long id, @RequestBody FitnessClassDTO fitnessClassDTO) {
        return new ResponseEntity<>(fitnessClassService.updateFitnessClass(id, fitnessClassDTO), HttpStatus.OK);
    }

    @DeleteMapping("/fitness-class/{id}")
    public ResponseEntity<Map<String, String>> deleteFitnessClass(@PathVariable Long id) {
        return new ResponseEntity<>(fitnessClassService.deleteFitnessClass(id), HttpStatus.OK);
    }

    @GetMapping("/fitness-classes/search/{keyword}")
    public ResponseEntity<List<FitnessClassDTO>> searchFitnessClasses(@PathVariable String keyword) {
        return new ResponseEntity<>(fitnessClassService.searchFitnessClasses(keyword), HttpStatus.OK);
    }

}
