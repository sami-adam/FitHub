package com.fithub.controller.accounting;

import com.fithub.dto.accounting.JournalDTO;
import com.fithub.service.accounting.JournalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class JournalController {
    private final JournalService journalService;

    @GetMapping("/v1/journals")
    public ResponseEntity<List<JournalDTO>> getJournals(){
        return ResponseEntity.ok(journalService.getJournals());
    }

    @PostMapping("/v1/journal")
    public ResponseEntity<JournalDTO> addJournal(@RequestBody JournalDTO journalDTO){
        return ResponseEntity.ok(journalService.addJournal(journalDTO));
    }

    @PutMapping("/v1/journal/{id}")
    public ResponseEntity<JournalDTO> updateJournal(@PathVariable Long id, @RequestBody JournalDTO journalDTO){
        return ResponseEntity.ok(journalService.updateJournal(id, journalDTO));
    }

    @DeleteMapping("/v1/journal/{id}")
    public ResponseEntity<?> deleteJournal(@PathVariable Long id){
        return ResponseEntity.ok(journalService.deleteJournal(id));
    }

    @GetMapping("/v1/journals/search/{keyword}")
    public ResponseEntity<List<JournalDTO>> searchJournals(@PathVariable String keyword){
        return ResponseEntity.ok(journalService.searchJournals(keyword));
    }
}
