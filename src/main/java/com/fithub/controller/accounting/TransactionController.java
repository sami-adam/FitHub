package com.fithub.controller.accounting;

import com.fithub.dto.accounting.TransactionDTO;
import com.fithub.service.accounting.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @GetMapping("/v1/transactions")
    public ResponseEntity<List<TransactionDTO>> getTransactions(){
        return ResponseEntity.ok(transactionService.getTransactions());
    }

    @PostMapping("/v1/transaction")
    public ResponseEntity<TransactionDTO> addTransaction(@RequestBody TransactionDTO transactionDTO){
        return ResponseEntity.ok(transactionService.addTransaction(transactionDTO));
    }

    @PutMapping("/v1/transaction/{id}")
    public ResponseEntity<TransactionDTO> updateTransaction(@PathVariable Long id, @RequestBody TransactionDTO transactionDTO){
        return ResponseEntity.ok(transactionService.updateTransaction(id, transactionDTO));
    }

    @DeleteMapping("/v1/transaction/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long id){
        transactionService.deleteTransaction(id);
        return ResponseEntity.ok().build();
    }
}
