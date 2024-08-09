package com.fithub.controller.accounting;

import com.fithub.dto.accounting.AccountDTO;
import com.fithub.service.accounting.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @GetMapping("/v1/accounts")
    public ResponseEntity<List<AccountDTO>> getAccounts(){
        return ResponseEntity.ok(accountService.getAccounts());
    }

    @PostMapping("/v1/account")
    public ResponseEntity<AccountDTO> addAccount(@RequestBody AccountDTO accountDTO){
        return ResponseEntity.ok(accountService.addAccount(accountDTO));
    }

    @PutMapping("/v1/account/{id}")
    public ResponseEntity<AccountDTO> updateAccount(@PathVariable Long id,@RequestBody AccountDTO accountDTO){
        return ResponseEntity.ok(accountService.updateAccount(id, accountDTO));
    }

    @DeleteMapping("/v1/account/{id}")
    public ResponseEntity<Map<String, String>> deleteAccount(@PathVariable Long id){
        return ResponseEntity.ok(accountService.deleteAccount(id));
    }
}
