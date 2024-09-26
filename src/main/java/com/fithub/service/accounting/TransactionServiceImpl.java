package com.fithub.service.accounting;

import com.fithub.config.SecurityContextGenerator;
import com.fithub.dto.accounting.AccountDTO;
import com.fithub.dto.accounting.EntryDTO;
import com.fithub.dto.accounting.TransactionDTO;
import com.fithub.exception.BadRequestException;
import com.fithub.exception.ResourceNotFoundException;
import com.fithub.model.accounting.Account;
import com.fithub.model.accounting.Entry;
import com.fithub.model.accounting.Journal;
import com.fithub.model.accounting.Transaction;
import com.fithub.repository.accounting.EntryRepository;
import com.fithub.repository.accounting.TransactionRepository;
import com.fithub.repository.address.CityRepository;
import com.fithub.service.odoo.OdooService;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hibernate.grammars.hql.HqlParser;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService{
    private final TransactionRepository transactionRepository;
    private final EntryRepository entryRepository;
    private final AccountService accountService;
    private final ModelMapper mapper = new ModelMapper();
    private final CityRepository cityRepository;
    private final JournalService journalService;
    private final Logger logger = org.slf4j.LoggerFactory.getLogger(TransactionServiceImpl.class);

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
    public List<TransactionDTO> getTransactions() {
        return transactionRepository.findAll().stream()
                .map(transaction -> mapper.map(transaction, TransactionDTO.class)).toList();
    }

    @Override
    public TransactionDTO getTransaction(Long id) {
        Transaction transaction = transactionRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Transaction not found with id: " + id));
        return mapper.map(transaction, TransactionDTO.class);
    }

    private void checkDebitCreditEquality(TransactionDTO transactionDTO){
        BigDecimal totalDebit = transactionDTO.getEntries().stream().map(EntryDTO::getDebit).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal totalCredit = transactionDTO.getEntries().stream().map(EntryDTO::getCredit).reduce(BigDecimal.ZERO, BigDecimal::add);
        if(totalDebit.compareTo(totalCredit) != 0){
            throw new BadRequestException("Total debit and credit must be equal");
        }
    }
    @Override
    public TransactionDTO addTransaction(TransactionDTO transactionDTO) {
        Transaction transaction = mapper.map(transactionDTO, Transaction.class);
        Transaction savedTransaction = transactionRepository.save(transaction);
        for(EntryDTO entryDTO: transactionDTO.getEntries()){
            entryDTO.setTransaction(mapper.map(savedTransaction, TransactionDTO.class));
            entryRepository.save(mapper.map(entryDTO, Entry.class));
        }
        checkDebitCreditEquality(transactionDTO);
        return mapper.map(transaction, TransactionDTO.class);
    }

    @Override
    @Transactional
    public TransactionDTO updateTransaction(Long id, TransactionDTO transactionDTO) {
        Transaction transaction = transactionRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Transaction not found with id: " + id));
        checkDebitCreditEquality(transactionDTO);
        if(transactionDTO.getDescription() != null){
            transaction.setDescription(transactionDTO.getDescription());
        }
        if(transactionDTO.getEntries() != null){
            List<Entry> entries = new ArrayList<>();
            // Remove deleted entries
            transaction.getEntries().clear();
            for(EntryDTO entryDTO: transactionDTO.getEntries()){
                if(entryDTO.getId() != null){
                    Entry entry = entryRepository.findById(entryDTO.getId()).orElseThrow(
                            ()-> new ResourceNotFoundException("Entry not found with id: " + entryDTO.getId()));
                    entry.setAccount(mapper.map(entryDTO.getAccount(), Account.class));
                    entry.setDebit(entryDTO.getDebit());
                    entry.setCredit(entryDTO.getCredit());
                    entry.setType(entryDTO.getType());
                    entry.setTransaction(transaction);
                    entries.add(entryRepository.save(entry));
                } else {
                    entryDTO.setTransaction(transactionDTO);
                    entries.add(entryRepository.save(mapper.map(entryDTO, Entry.class)));
                }
            }
            transaction.setEntries(entries);
        }
        if(transactionDTO.getJournal() != null){
            transaction.setJournal(mapper.map(transactionDTO.getJournal(), Journal.class));
        }
        transactionRepository.save(transaction);
        return mapper.map(transaction, TransactionDTO.class);
    }

    @Override
    public void deleteTransaction(Long id) {
    }

    @Override
    public void postTransaction(Long id) {
        Transaction transaction = transactionRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Transaction not found with id: " + id));
        for(Entry entry: transaction.getEntries()){
            if (entry.getType().equals(Entry.Type.DEBIT)){
                accountService.debitAccount(entry.getAccount().getId(), entry.getDebit());
            } else {
                accountService.creditAccount(entry.getAccount().getId(), entry.getCredit());
            }
        }
        transaction.setStatus(Transaction.Status.POSTED);
        transactionRepository.save(transaction);
    }

    @Override
    public List<TransactionDTO> searchTransactions(String query) {
        return transactionRepository.findByReferenceContainingIgnoreCaseOrDescriptionContainingIgnoreCase(query, query).stream()
                .map(transaction -> mapper.map(transaction, TransactionDTO.class)).toList();
    }
    public static LocalDateTime convertToLocalDateTime(String date) {
        // Parse the string as LocalDate
        if(Objects.equals(date, "false")){
            date = "2021-01-01";
        }
        LocalDate localDate = LocalDate.parse(date);
        // Convert LocalDate to LocalDateTime at the start of the day (midnight)
        return localDate.atStartOfDay();
    }

    // Odoo Integration
    void syncTransactions(SecurityContext context){
        logger.info("Syncing transactions with Odoo");
        try {
            odooService.authenticate(odooUrl, odooDb, odooLogin, odooPassword);
            String response = odooService.getRecords(odooUrl, "account.move", new String[]{"name", "journal_id", "line_ids", "invoice_date"});
            JsonObject jsonObject = JsonParser.parseString(response).getAsJsonObject();
            JsonArray transactions = jsonObject.get("result").getAsJsonArray();
            for(JsonElement jsonElement : transactions){
                JsonObject transactionJson = jsonElement.getAsJsonObject();
                String name = transactionJson.get("name").getAsString();
                Long journalId = transactionJson.get("journal_id").getAsJsonArray().get(0).getAsLong();

                String invoiceDate = transactionJson.get("invoice_date").getAsString();
                JsonArray lineIds = transactionJson.get("line_ids").getAsJsonArray();
                List<Transaction> existingTransactions = transactionRepository.findByReference(name);
                if(!existingTransactions.isEmpty()) {
                    continue;
                }
                String journalRecord = odooService.getRecord(odooUrl, "account.journal", new String[]{"code", "name", "type"}, journalId);
                String journalCode = JsonParser.parseString(journalRecord).getAsJsonObject().get("result").getAsJsonArray().get(0).getAsJsonObject().get("code").getAsString();
                List<Entry> entries = new ArrayList<>();
                for(JsonElement lineId : lineIds){
                    String lineRecord = odooService.getRecord(odooUrl, "account.move.line", new String[]{"account_id", "debit", "credit", "name", "date"}, lineId.getAsLong());
                    JsonObject lineObject = JsonParser.parseString(lineRecord).getAsJsonObject().get("result").getAsJsonArray().get(0).getAsJsonObject();
                    String accountCode = lineObject.get("account_id").getAsJsonArray().get(1).getAsString().split(" ")[0];
                    List<AccountDTO> accounts = accountService.getAccountsByCode(accountCode);
                    Entry entry = new Entry();
                    entry.setType(lineObject.get("debit").getAsBigDecimal().compareTo(BigDecimal.ZERO) == 0 ? Entry.Type.CREDIT : Entry.Type.DEBIT);
                    entry.setDebit(lineObject.get("debit").getAsBigDecimal());
                    entry.setCredit(lineObject.get("credit").getAsBigDecimal());
                    entry.setAccount(mapper.map(accounts.getFirst(), Account.class));
                    entries.add(entry);
                }
                Transaction transaction = new Transaction();
                transaction.setReference(name);
                transaction.setDescription("Odoo Transaction" + name);
                transaction.setJournal(mapper.map(journalService.getJournalsByCode(journalCode).getFirst(), Journal.class));
                transaction.setEntries(entries);
                transaction.setTimestamp(convertToLocalDateTime(invoiceDate));
                transaction.setStatus(Transaction.Status.POSTED);
                addTransaction(mapper.map(transaction, TransactionDTO.class));
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
            syncTransactions(context);
            //System.out.println("Syncing transactions with Odoo");
        } finally {
            //context.setAuthentication(null);
        }
    }
}
