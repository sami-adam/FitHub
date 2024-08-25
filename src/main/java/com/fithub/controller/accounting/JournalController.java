package com.fithub.controller.accounting;

import com.fithub.dto.accounting.JournalDTO;
import com.fithub.service.accounting.JournalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class JournalController {
    private final JournalService journalService;

    @GetMapping("/journals")
    public ResponseEntity<List<JournalDTO>> getJournals(){
        return ResponseEntity.ok(journalService.getJournals());
    }

    @GetMapping("/journal/{id}")
    public ResponseEntity<JournalDTO> getJournal(@PathVariable Long id){
        return ResponseEntity.ok(journalService.getJournal(id));
    }

    @PostMapping("/journal")
    public ResponseEntity<JournalDTO> addJournal(@RequestBody JournalDTO journalDTO){
        return ResponseEntity.ok(journalService.addJournal(journalDTO));
    }

    @PutMapping("/journal/{id}")
    public ResponseEntity<JournalDTO> updateJournal(@PathVariable Long id, @RequestBody JournalDTO journalDTO){
        return ResponseEntity.ok(journalService.updateJournal(id, journalDTO));
    }

    @DeleteMapping("/journal/{id}")
    public ResponseEntity<?> deleteJournal(@PathVariable Long id){
        return ResponseEntity.ok(journalService.deleteJournal(id));
    }

    @GetMapping("/journals/search/{keyword}")
    public ResponseEntity<List<JournalDTO>> searchJournals(@PathVariable String keyword){
        return ResponseEntity.ok(journalService.searchJournals(keyword));
    }
}
