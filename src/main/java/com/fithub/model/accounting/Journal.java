package com.fithub.model.accounting;

import com.fithub.model.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "journals")
@Data
public class Journal extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "code")
    private String code;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private Type type;

    @JoinColumn(name = "account_id")
    @ManyToOne
    private Account account;


    public enum Type {
        SALE, PURCHASE, CASH, BANK, GENERAL
    }
}
