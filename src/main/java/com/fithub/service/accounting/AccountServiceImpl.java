package com.fithub.service.accounting;

import com.fithub.dto.accounting.AccountDTO;
import com.fithub.exception.DuplicateException;
import com.fithub.exception.ResourceNotFoundException;
import com.fithub.model.accounting.Account;
import com.fithub.repository.accounting.AccountRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService{
    private final AccountRepository accountRepository;
    private final ModelMapper mapper = new ModelMapper();

    @Override
    public AccountDTO addAccount(AccountDTO accountDTO) {
        List<Account> existingAccounts = accountRepository.findByCode(accountDTO.getCode());
        if(!existingAccounts.isEmpty()) {
            throw new DuplicateException("Account with code: " + accountDTO.getCode() + " already exists");
        }
        Account account = mapper.map(accountDTO, Account.class);
        account = accountRepository.save(account);
        return mapper.map(account, AccountDTO.class);
    }

    @Override
    public List<AccountDTO> getAccounts() {
        return accountRepository.findAll().stream().map(account -> mapper.map(account, AccountDTO.class)).toList();
    }

    @Override
    public AccountDTO getAccount(Long id) {
        Account account = accountRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Account not found with id: " + id));
        return mapper.map(account, AccountDTO.class);
    }

    @Override
    public AccountDTO updateAccount(Long id, AccountDTO accountDTO) {
        List<Account> existingAccounts = accountRepository.findByCode(accountDTO.getCode());
        if(!existingAccounts.isEmpty() && !existingAccounts.getFirst().getId().equals(id)) {
            throw new DuplicateException("Account with code: " + accountDTO.getCode() + " already exists");
        }
        Account account = accountRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Account not found with id: " + id));
        if(accountDTO.getName() != null) account.setName(accountDTO.getName());
        if(accountDTO.getCode() != null) account.setCode(accountDTO.getCode());
        if(accountDTO.getType() != null) account.setType(accountDTO.getType());

        account = accountRepository.save(account);
        return mapper.map(account, AccountDTO.class);
    }

    @Transactional
    public AccountDTO creditAccount(Long id, BigDecimal amount) {
        Account account = accountRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Account not found with id: " + id));
        account.setCredit(account.getCredit().add(amount));
        account.setBalance(account.getBalance().add(amount));
        account = accountRepository.save(account);
        return mapper.map(account, AccountDTO.class);
    }

    @Transactional
    public AccountDTO debitAccount(Long id, BigDecimal amount) {
        Account account = accountRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Account not found with id: " + id));
        account.setDebit(account.getDebit().add(amount));
        account.setBalance(account.getBalance().subtract(amount));
        account = accountRepository.save(account);
        return mapper.map(account, AccountDTO.class);
    }

    @Override
    public Map<String, String> deleteAccount(Long id) {
        Account account = accountRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Account not found with id: " + id));
        accountRepository.delete(account);
        return Map.of("message", "Account deleted successfully", "status", "success");
    }

    @Override
    public List<AccountDTO> searchAccounts(String keyword) {
        Account.Type type = null;
        try {
            type = Account.Type.valueOf(keyword.toUpperCase());
        } catch (IllegalArgumentException e) {
            // ignore
        }
        return accountRepository.findByCodeContainingIgnoreCaseOrNameContainingIgnoreCaseOrTypeEquals(keyword, keyword, type)
                .stream().map(account -> mapper.map(account, AccountDTO.class)).toList();
    }
}
