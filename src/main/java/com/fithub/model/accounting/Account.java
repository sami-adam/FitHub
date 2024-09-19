package com.fithub.model.accounting;

import com.fithub.model.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Table(name = "accounts")
@Data
public class Account extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String code;

    @Column(nullable = false)
    private String name;

    private BigDecimal credit = BigDecimal.ZERO;
    private BigDecimal debit = BigDecimal.ZERO;

    @Column(nullable = false)
    private BigDecimal balance = BigDecimal.ZERO;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private Type type;


    public enum Type {
        ASSET_RECEIVABLE,
        ASSET_CASH,
        ASSET_CURRENT,
        ASSET_NON_CURRENT,
        ASSET_PREPAYMENTS,
        ASSET_FIXED,
        LIABILITY_PAYABLE,
        LIABILITY_CREDIT_CARD,
        LIABILITY_CURRENT,
        LIABILITY_NON_CURRENT,
        EQUITY,
        EQUITY_UNAFFECTED,
        INCOME,
        INCOME_OTHER,
        EXPENSE,
        EXPENSE_DEPRECIATION,
        EXPENSE_DIRECT_COST,
        OFF_BALANCE,
        RECEIVABLE,

    }
}
