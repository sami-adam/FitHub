package com.fithub.repository.accounting;

import com.fithub.model.accounting.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByReferenceContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String reference, String description);
    List<Transaction> findByReference(String reference);
}
