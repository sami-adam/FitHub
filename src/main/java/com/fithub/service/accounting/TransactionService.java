package com.fithub.service.accounting;

import com.fithub.dto.accounting.TransactionDTO;

import java.util.List;

public interface TransactionService {
    List<TransactionDTO> getTransactions();
    TransactionDTO addTransaction(TransactionDTO transactionDTO);
    TransactionDTO updateTransaction(Long id, TransactionDTO transactionDTO);
    void deleteTransaction(Long id);
    void postTransaction(Long id);
    List<TransactionDTO> searchTransactions(String query);
}
