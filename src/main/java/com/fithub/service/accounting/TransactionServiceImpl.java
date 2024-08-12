package com.fithub.service.accounting;

import com.fithub.dto.accounting.EntryDTO;
import com.fithub.dto.accounting.TransactionDTO;
import com.fithub.exception.ResourceNotFoundException;
import com.fithub.model.accounting.Entry;
import com.fithub.model.accounting.Journal;
import com.fithub.model.accounting.Transaction;
import com.fithub.repository.accounting.EntryRepository;
import com.fithub.repository.accounting.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService{
    private final TransactionRepository transactionRepository;
    private final EntryRepository entryRepository;
    private final AccountService accountService;
    private final ModelMapper mapper = new ModelMapper();

    @Override
    public List<TransactionDTO> getTransactions() {
        return transactionRepository.findAll().stream()
                .map(transaction -> mapper.map(transaction, TransactionDTO.class)).toList();
    }

    @Override
    public TransactionDTO addTransaction(TransactionDTO transactionDTO) {
        Transaction transaction = mapper.map(transactionDTO, Transaction.class);
        Transaction savedTransaction = transactionRepository.save(transaction);
        for(EntryDTO entryDTO: transactionDTO.getEntries()){
            entryDTO.setTransaction(mapper.map(savedTransaction, TransactionDTO.class));
            entryRepository.save(mapper.map(entryDTO, Entry.class));
        }
        return mapper.map(transaction, TransactionDTO.class);
    }

    @Override
    public TransactionDTO updateTransaction(Long id, TransactionDTO transactionDTO) {
        Transaction transaction = transactionRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Transaction not found with id: " + id));

        if(transactionDTO.getDescription() != null){
            transaction.setDescription(transactionDTO.getDescription());
        }
        if(transactionDTO.getEntries() != null){
            List<Entry> entries = new ArrayList<>();
            for(EntryDTO entryDTO: transactionDTO.getEntries()){
                entries.add(entryRepository.save(mapper.map(entryDTO, Entry.class)));
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
    public Map<String, String> deleteTransaction(Long id) {
        return Map.of();
    }

    @Override
    public String postTransaction(Long id) {
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
        return "Transaction posted successfully";
    }
}
