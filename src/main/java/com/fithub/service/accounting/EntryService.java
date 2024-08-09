package com.fithub.service.accounting;

import com.fithub.dto.accounting.EntryDTO;

import java.util.List;
import java.util.Map;

public interface EntryService {
    List<EntryDTO> getEntries();
    EntryDTO addEntry(EntryDTO entryDTO);
    EntryDTO updateEntry(Long id, EntryDTO entryDTO);
    Map<String, String> deleteEntry(Long id);
    List<EntryDTO> searchEntries(String keyword);
}
