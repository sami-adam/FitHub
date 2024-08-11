package com.fithub.dto.accounting;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fithub.model.accounting.Entry;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class EntryDTO {
    private Long id;
    @NotNull
    private AccountDTO account;
    @JsonIgnoreProperties("entries")
    private TransactionDTO transaction;
    private BigDecimal debit = BigDecimal.ZERO;
    private BigDecimal credit = BigDecimal.ZERO;
    @NotNull
    private Entry.Type type;
}
