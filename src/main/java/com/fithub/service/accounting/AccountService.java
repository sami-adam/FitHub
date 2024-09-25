package com.fithub.service.accounting;

import com.fithub.dto.accounting.AccountDTO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface AccountService {
    AccountDTO addAccount(AccountDTO accountDTO);
    List<AccountDTO> getAccounts();
    AccountDTO getAccount(Long id);
    AccountDTO updateAccount(Long id, AccountDTO accountDTO);
    Map<String, String> deleteAccount(Long id);
    List<AccountDTO> searchAccounts(String keyword);
    AccountDTO creditAccount(Long id, BigDecimal amount);
    AccountDTO debitAccount(Long id, BigDecimal amount);
    List<AccountDTO> getAccountsByCode(String code);
}
