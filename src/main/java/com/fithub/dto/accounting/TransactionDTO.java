package com.fithub.dto.accounting;

import com.fithub.model.accounting.Transaction;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class TransactionDTO {
    private Long id;
    private LocalDateTime timestamp;
    private String description;
    private List<EntryDTO> entries;
    private Transaction.Status status;
}
