package com.fithub.service.accounting;

import com.fithub.dto.accounting.JournalDTO;

import java.util.List;
import java.util.Map;

public interface JournalService {
    List<JournalDTO> getJournals();
    JournalDTO getJournal(Long id);
    JournalDTO addJournal(JournalDTO journalDTO);
    JournalDTO updateJournal(Long id, JournalDTO journalDTO);
    Map<String, String> deleteJournal(Long id);
    List<JournalDTO> searchJournals(String keyword);
}
