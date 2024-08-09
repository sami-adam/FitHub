package com.fithub.dto.accounting;

import com.fithub.model.accounting.Account;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountDTO {
    private Long id;
    @NotNull
    private String code;
    @NotNull
    private String name;
    private BigDecimal credit = BigDecimal.ZERO;
    private BigDecimal debit = BigDecimal.ZERO;
    private BigDecimal balance = BigDecimal.ZERO;
    private Account.Type type;
}
