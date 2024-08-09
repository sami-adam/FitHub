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
        RECEIVABLE, BANk, CASH, PREPAYMENT, // Assets
        PAYABLE, CREDIT_CARD, CURRENT_LIABILITIES, NON_CURRENT_LIABILITIES, // Liabilities
        EQUITY, CURRENT_YEAR_EARNINGS, // Equity
        INCOME, OTHER_INCOME, // Income
        EXPENSE, COST_OF_REVENUE // Expense
    }
}
