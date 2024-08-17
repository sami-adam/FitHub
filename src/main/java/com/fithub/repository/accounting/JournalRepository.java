package com.fithub.repository.accounting;

import com.fithub.model.accounting.Journal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JournalRepository extends JpaRepository<Journal, Long> {
    List<Journal> findByCodeContainingIgnoreCaseOrNameContainingIgnoreCaseOrTypeEquals(String code, String name, Journal.Type type);
}
