package com.fithub.service.accounting;

import com.fithub.dto.accounting.JournalDTO;
import com.fithub.exception.ResourceNotFoundException;
import com.fithub.model.accounting.Account;
import com.fithub.model.accounting.Journal;
import com.fithub.repository.accounting.JournalRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class JournalServiceImpl implements JournalService{
    private final JournalRepository journalRepository;
    private final ModelMapper mapper = new ModelMapper();

    @Override
    public List<JournalDTO> getJournals() {
        return journalRepository.findAll().stream()
                .map(journal -> mapper.map(journal, JournalDTO.class))
                .toList();
    }

    @Override
    public JournalDTO addJournal(JournalDTO journalDTO) {
        Journal journal = mapper.map(journalDTO, Journal.class);
        journalRepository.save(journal);
        return mapper.map(journal, JournalDTO.class);
    }

    @Override
    public JournalDTO updateJournal(Long id, JournalDTO journalDTO) {
        Journal journal = journalRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Journal not found with id: " + id)
        );
        if(journalDTO.getName() != null) journal.setName(journalDTO.getName());
        if(journalDTO.getCode() != null) journal.setCode(journalDTO.getCode());
        if(journalDTO.getDescription() != null) journal.setDescription(journalDTO.getDescription());
        if(journalDTO.getType() != null) journal.setType(journalDTO.getType());
        if(journalDTO.getAccount() != null) journal.setAccount(mapper.map(journalDTO.getAccount(), Account.class));

        journalRepository.save(journal);
        return mapper.map(journal, JournalDTO.class);
    }

    @Override
    public Map<String, String> deleteJournal(Long id) {
        Journal journal = journalRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Journal not found with id: " + id)
        );
        journalRepository.delete(journal);
        return Map.of("message", "Journal deleted successfully", "status", "success");
    }

    @Override
    public List<JournalDTO> searchJournals(String keyword) {
        Journal.Type type = null;
        try {
            type = Journal.Type.valueOf(keyword);
        } catch (IllegalArgumentException ignored) {}

        return journalRepository.findByCodeContainingIgnoreCaseOrNameContainingIgnoreCaseOrTypeEquals(keyword, keyword, type).stream()
                .map(journal -> mapper.map(journal, JournalDTO.class))
                .toList();
    }
}
