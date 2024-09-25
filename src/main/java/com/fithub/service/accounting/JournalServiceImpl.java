package com.fithub.service.accounting;

import com.fithub.config.SecurityContextGenerator;
import com.fithub.dto.accounting.JournalDTO;
import com.fithub.exception.DuplicateException;
import com.fithub.exception.ResourceNotFoundException;
import com.fithub.model.accounting.Account;
import com.fithub.model.accounting.Journal;
import com.fithub.repository.accounting.JournalRepository;
import com.fithub.service.odoo.OdooService;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class JournalServiceImpl implements JournalService{
    private final JournalRepository journalRepository;
    private final ModelMapper mapper = new ModelMapper();
    private final Logger logger = org.slf4j.LoggerFactory.getLogger(JournalServiceImpl.class);

    @Autowired
    private SecurityContextGenerator securityContextGenerator;
    // Odoo Properties
    @Value("${odoo.url}")
    private String odooUrl;
    @Value("${odoo.db}")
    private String odooDb;
    @Value("${odoo.username}")
    private String odooLogin;
    @Value("${odoo.password}")
    private String odooPassword;
    private final OdooService odooService;

    @Override
    public List<JournalDTO> getJournals() {
        return journalRepository.findAll().stream()
                .map(journal -> mapper.map(journal, JournalDTO.class))
                .toList();
    }

    @Override
    public JournalDTO getJournal(Long id) {
        Journal journal = journalRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Journal not found with id: " + id)
        );
        return mapper.map(journal, JournalDTO.class);
    }

    @Override
    public JournalDTO addJournal(JournalDTO journalDTO) {
        List<Journal> existingJournals = journalRepository.findByCode(journalDTO.getCode());
        if(!existingJournals.isEmpty()) {
            throw new DuplicateException("Journal with code: " + journalDTO.getCode() + " already exists");
        }
        Journal journal = mapper.map(journalDTO, Journal.class);
        return mapper.map(journalRepository.save(journal), JournalDTO.class);
    }

    @Override
    public JournalDTO updateJournal(Long id, JournalDTO journalDTO) {
        List<Journal> existingJournals = journalRepository.findByCode(journalDTO.getCode());
        if(!existingJournals.isEmpty() && !existingJournals.getFirst().getId().equals(id)) {
            throw new DuplicateException("Journal with code: " + journalDTO.getCode() + " already exists");
        }
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

    public List<JournalDTO> getJournalsByCode(String code) {
        return journalRepository.findByCode(code).stream()
                .map(journal -> mapper.map(journal, JournalDTO.class))
                .toList();
    }

    // Odoo Integration
    void syncJournals(SecurityContext context){
        logger.info("Syncing journals with Odoo");
        try {
            odooService.authenticate(odooUrl, odooDb, odooLogin, odooPassword);
            String response = odooService.getRecords(odooUrl, "account.journal", new String[]{"code", "name", "type"});
            JsonObject jsonObject = JsonParser.parseString(response).getAsJsonObject();
            JsonArray journals = jsonObject.get("result").getAsJsonArray();
            for(JsonElement jsonElement : journals){
                JsonObject journalJson = jsonElement.getAsJsonObject();
                String code = journalJson.get("code").getAsString();
                String name = journalJson.get("name").getAsString();
                String type = journalJson.get("type").getAsString();
                List<Journal> existingJournals = journalRepository.findByCode(code.toUpperCase());
                if(existingJournals.isEmpty()) {
                    Journal journal = new Journal();
                    journal.setCode(code);
                    journal.setName(name);
                    try {
                        journal.setType(Journal.Type.valueOf(type.toUpperCase()));
                        journalRepository.save(journal);
                    } catch (IllegalArgumentException e) {
                        logger.error("Invalid journal type: " + type);
                    }
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    //@Transactional
    @Scheduled(fixedRate = 1000 * 60 * 30) // Adjust the fixedRate as needed
    public void performTask() {
        SecurityContext context = securityContextGenerator.createSecurityContext();
        try {
            syncJournals(context);
            //System.out.println("Syncing journals with Odoo");
        } finally {
            //context.setAuthentication(null);
        }
    }
}
