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
public class FitnessClassController {
    private final FitnessClassService fitnessClassService;

    public FitnessClassController(FitnessClassService fitnessClassService) {
        this.fitnessClassService = fitnessClassService;
    }

    @GetMapping("/fitnessClasses")
    public ResponseEntity<List<FitnessClassDTO>> getFitnessClasses() {
        return new ResponseEntity<>(fitnessClassService.getFitnessClasses(), HttpStatus.OK);
    }

    @PostMapping("/fitnessClass")
    public ResponseEntity<FitnessClassDTO> addFitnessClass(FitnessClassDTO fitnessClassDTO) {
        return new ResponseEntity<>(fitnessClassService.addFitnessClass(fitnessClassDTO), HttpStatus.CREATED);
    }

    @PutMapping("/fitnessClass/{id}")
    public ResponseEntity<FitnessClassDTO> updateFitnessClass(@PathVariable Long id, FitnessClassDTO fitnessClassDTO) {
        return new ResponseEntity<>(fitnessClassService.updateFitnessClass(id, fitnessClassDTO), HttpStatus.OK);
    }

    @DeleteMapping("/fitnessClass/{id}")
    public ResponseEntity<Map<String, String>> deleteFitnessClass(@PathVariable Long id) {
        return new ResponseEntity<>(fitnessClassService.deleteFitnessClass(id), HttpStatus.OK);
    }

    @GetMapping("/fitnessClasses/search/{keyword}")
    public ResponseEntity<List<FitnessClassDTO>> searchFitnessClasses(@PathVariable String keyword) {
        return new ResponseEntity<>(fitnessClassService.searchFitnessClasses(keyword), HttpStatus.OK);
    }

}
