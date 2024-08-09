package com.fithub.model.accounting;

import com.fithub.model.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Table(name = "entries")
@Data
public class Entry extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Account account;

    @ManyToOne
    private Transaction transaction;


    private BigDecimal debit = BigDecimal.ZERO;
    private BigDecimal credit = BigDecimal.ZERO;

    @Column(nullable = false)
    private Type type;

    // Getters and Setters

    public enum Type {
        DEBIT, CREDIT
    }
}
