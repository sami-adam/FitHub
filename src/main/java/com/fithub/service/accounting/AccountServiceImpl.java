package com.fithub.service.accounting;

import com.fithub.config.SecurityContextGenerator;
import com.fithub.dto.accounting.AccountDTO;
import com.fithub.exception.DuplicateException;
import com.fithub.exception.ResourceNotFoundException;
import com.fithub.model.accounting.Account;
import com.fithub.repository.accounting.AccountRepository;
import com.fithub.service.odoo.OdooService;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService{
    private final AccountRepository accountRepository;
    private final ModelMapper mapper = new ModelMapper();
    private final Logger logger = org.slf4j.LoggerFactory.getLogger(AccountServiceImpl.class);

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
    public List<AccountDTO> getAccountsByCode(String code) {
        return accountRepository.findByCode(code).stream().map(account -> mapper.map(account, AccountDTO.class)).toList();
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

    // Odoo Integration
    void syncAccounts(SecurityContext context){
        logger.info("Syncing accounts with Odoo");
        try {
            odooService.authenticate(odooUrl, odooDb, odooLogin, odooPassword);
            String response = odooService.getRecords(odooUrl, "account.account", new String[]{"code", "name", "account_type"});
            JsonObject jsonObject = JsonParser.parseString(response).getAsJsonObject();
            JsonArray accounts = jsonObject.get("result").getAsJsonArray();
            for(JsonElement jsonElement : accounts){
                JsonObject accountJson = jsonElement.getAsJsonObject();
                String code = accountJson.get("code").getAsString();
                String name = accountJson.get("name").getAsString();
                String type = accountJson.get("account_type").getAsString();
                List<Account> existingAccounts = accountRepository.findByCode(code);
                if(existingAccounts.isEmpty()) {
                    Account account = new Account();
                    account.setCode(code);
                    account.setName(name);
                    try {
                        account.setType(Account.Type.valueOf(type.toUpperCase()));
                        accountRepository.save(account);
                    } catch (IllegalArgumentException e) {
                        logger.error("Invalid account type: " + type);
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
            syncAccounts(context);
            //System.out.println("Syncing accounts with Odoo");
        } finally {
            //context.setAuthentication(null);
        }
    }
}
