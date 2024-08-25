package com.fithub.controller.accounting;

import com.fithub.dto.accounting.AccountDTO;
import com.fithub.service.accounting.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @GetMapping("/accounts")
    public ResponseEntity<List<AccountDTO>> getAccounts(){
        return ResponseEntity.ok(accountService.getAccounts());
    }

    @GetMapping("/account/{id}")
    public ResponseEntity<AccountDTO> getAccount(@PathVariable Long id){
        return ResponseEntity.ok(accountService.getAccount(id));
    }

    @PostMapping("/account")
    public ResponseEntity<AccountDTO> addAccount(@RequestBody AccountDTO accountDTO){
        return ResponseEntity.ok(accountService.addAccount(accountDTO));
    }

    @PutMapping("/account/{id}")
    public ResponseEntity<AccountDTO> updateAccount(@PathVariable Long id,@RequestBody AccountDTO accountDTO){
        return ResponseEntity.ok(accountService.updateAccount(id, accountDTO));
    }

    @DeleteMapping("/account/{id}")
    public ResponseEntity<Map<String, String>> deleteAccount(@PathVariable Long id){
        return ResponseEntity.ok(accountService.deleteAccount(id));
    }

    @GetMapping("/accounts/search/{keyword}")
    public ResponseEntity<List<AccountDTO>> searchAccounts(@PathVariable String keyword){
        return ResponseEntity.ok(accountService.searchAccounts(keyword));
    }
}
