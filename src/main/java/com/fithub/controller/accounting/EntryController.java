package com.fithub.controller.accounting;

import com.fithub.dto.accounting.EntryDTO;
import com.fithub.service.accounting.EntryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class EntryController {
    private final EntryService entryService;

    @GetMapping("/v1/entries")
    public ResponseEntity<List<EntryDTO>> getEntries(){
        return ResponseEntity.ok(entryService.getEntries());
    }

    @PostMapping("/v1/entry")
    public ResponseEntity<EntryDTO> addEntry(@RequestBody EntryDTO entryDTO){
        return ResponseEntity.ok(entryService.addEntry(entryDTO));
    }

    @PutMapping("/v1/entry/{id}")
    public ResponseEntity<EntryDTO> updateEntry(@PathVariable Long id,@RequestBody EntryDTO entryDTO){
        return ResponseEntity.ok(entryService.updateEntry(id, entryDTO));
    }

    @DeleteMapping("/v1/entry/{id}")
    public ResponseEntity<Map<String, String>> deleteEntry(@PathVariable Long id){
        return ResponseEntity.ok(entryService.deleteEntry(id));
    }

}
