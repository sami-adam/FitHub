package com.fithub.controller.product;

import com.fithub.dto.product.BenefitDTO;
import com.fithub.service.product.BenefitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class BenefitController {
    private final BenefitService benefitService;

    @GetMapping("/benefits")
    public ResponseEntity<List<BenefitDTO>> getBenefits() {
        return new ResponseEntity<>(benefitService.getBenefits(), HttpStatus.OK);
    }

    @PostMapping("/benefit")
    public ResponseEntity<BenefitDTO> addBenefit(@RequestBody BenefitDTO benefitDTO) {
        return new ResponseEntity<>(benefitService.addBenefit(benefitDTO), HttpStatus.CREATED);
    }

    @PutMapping("/benefit/{id}")
    public ResponseEntity<BenefitDTO> updateBenefit(@PathVariable Long id, @RequestBody BenefitDTO benefitDTO) {
        return new ResponseEntity<>(benefitService.updateBenefit(id, benefitDTO), HttpStatus.OK);
    }

    @DeleteMapping("/benefit/{id}")
    public ResponseEntity<?> deleteBenefit(@PathVariable Long id) {
        return new ResponseEntity<>(benefitService.deleteBenefit(id), HttpStatus.OK);
    }

    @GetMapping("/benefits/search/{keyword}")
    public ResponseEntity<List<BenefitDTO>> searchBenefits(@PathVariable String keyword) {
        return new ResponseEntity<>(benefitService.searchBenefits(keyword), HttpStatus.OK);
    }
}
