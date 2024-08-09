package com.fithub.service.accounting;

import com.fithub.dto.accounting.EntryDTO;
import com.fithub.exception.ResourceNotFoundException;
import com.fithub.model.accounting.Account;
import com.fithub.model.accounting.Entry;
import com.fithub.repository.accounting.EntryRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EntryServiceImpl implements EntryService{
    private final EntryRepository entryRepository;
    private final ModelMapper mapper = new ModelMapper();

    @Override
    public List<EntryDTO> getEntries() {
        return entryRepository.findAll().stream().map(entry -> mapper.map(entry, EntryDTO.class)).toList();
    }

    @Override
    public EntryDTO addEntry(EntryDTO entryDTO) {
        Entry entry = mapper.map(entryDTO, Entry.class);
        entryRepository.save(entry);
        return mapper.map(entry, EntryDTO.class);
    }

    @Override
    public EntryDTO updateEntry(Long id, EntryDTO entryDTO) {
        Entry entry = entryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Entry not found"));
        if(entryDTO.getDebit() != null) entry.setDebit(entryDTO.getDebit());
        if(entryDTO.getCredit() != null) entry.setCredit(entryDTO.getCredit());
        if(entryDTO.getAccount() != null) entry.setAccount(mapper.map(entryDTO.getAccount(), Account.class));

        entryRepository.save(entry);
        return mapper.map(entry, EntryDTO.class);
    }

    @Override
    public Map<String, String> deleteEntry(Long id) {
        Entry entry = entryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Entry not found"));
        entryRepository.delete(entry);
        return Map.of("message", "Entry deleted successfully", "status", "success");
    }

    @Override
    public List<EntryDTO> searchEntries(String keyword) {
        return List.of();
    }
}
