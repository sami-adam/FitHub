package com.fithub.controller.base;

import com.fithub.dto.base.TaxDTO;
import com.fithub.service.base.TaxService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
public class TaxController {
    private final TaxService taxService;

    public TaxController(TaxService taxService) {
        this.taxService = taxService;
    }

    @GetMapping("/taxes")
    public ResponseEntity<List<TaxDTO>> getAllTaxes() {
        return ResponseEntity.ok(taxService.getAllTaxes());
    }

    @GetMapping("/tax/{id}")
    public ResponseEntity<TaxDTO> getTax(@PathVariable Long id) {
        return ResponseEntity.ok(taxService.getTaxById(id));
    }

    @PostMapping("/tax")
    public ResponseEntity<TaxDTO> add(@RequestBody TaxDTO taxDTO) {
        return ResponseEntity.ok(taxService.addTax(taxDTO));
    }

    @PutMapping("/tax/{id}")
    public ResponseEntity<TaxDTO> update(@PathVariable Long id, @RequestBody TaxDTO taxDTO) {
        return ResponseEntity.ok(taxService.updateTax(id, taxDTO));
    }

    @DeleteMapping("/tax/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return ResponseEntity.ok(taxService.deleteTax(id));
    }
}
