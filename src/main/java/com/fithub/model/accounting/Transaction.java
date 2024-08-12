package com.fithub.model.accounting;

import com.fithub.model.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "transactions")
@Data
public class Transaction extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String reference;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    @Column(length = 500)
    private String description;

    @OneToMany(mappedBy = "transaction")
    private List<Entry> entries;

    @JoinColumn(name = "journal_id")
    @ManyToOne
    private Journal journal;

    private Status status;

    public enum Status {
        DRAFT, POSTED, CANCELLED
    }

    @PostPersist
    public void postPersist() {
        reference = "TRX" + String.format("%06d", id);
    }

    @PreUpdate
    public void preUpdate() {
        if(reference == null) {
            reference = "TRX" + String.format("%06d", id);
        }
    }
}

