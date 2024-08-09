package com.fithub.service.accounting;

import com.fithub.dto.accounting.TransactionDTO;

import java.util.List;
import java.util.Map;

public interface TransactionService {
    List<TransactionDTO> getTransactions();
    TransactionDTO addTransaction(TransactionDTO transactionDTO);
    TransactionDTO updateTransaction(Long id, TransactionDTO transactionDTO);
    Map<String, String> deleteTransaction(Long id);
    String post(Long id);
}
