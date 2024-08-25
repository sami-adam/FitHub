package com.fithub.controller.accounting;

import com.fithub.dto.accounting.EntryDTO;
import com.fithub.service.accounting.EntryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class EntryController {
    private final EntryService entryService;

    @GetMapping("/entries")
    public ResponseEntity<List<EntryDTO>> getEntries(){
        return ResponseEntity.ok(entryService.getEntries());
    }

    @GetMapping("/entry/{id}")
    public ResponseEntity<EntryDTO> getEntry(@PathVariable Long id){
        return ResponseEntity.ok(entryService.getEntry(id));
    }

    @PostMapping("/entry")
    public ResponseEntity<EntryDTO> addEntry(@RequestBody EntryDTO entryDTO){
        return ResponseEntity.ok(entryService.addEntry(entryDTO));
    }

    @PutMapping("/entry/{id}")
    public ResponseEntity<EntryDTO> updateEntry(@PathVariable Long id,@RequestBody EntryDTO entryDTO){
        return ResponseEntity.ok(entryService.updateEntry(id, entryDTO));
    }

    @DeleteMapping("/entry/{id}")
    public ResponseEntity<Map<String, String>> deleteEntry(@PathVariable Long id){
        return ResponseEntity.ok(entryService.deleteEntry(id));
    }

    @GetMapping("/entries/search/{keyword}")
    public ResponseEntity<List<EntryDTO>> searchEntries(@PathVariable String keyword){
        return ResponseEntity.ok(entryService.searchEntries(keyword));
    }

}
