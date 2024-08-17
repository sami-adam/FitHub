package com.fithub.repository.accounting;

import com.fithub.model.accounting.Entry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EntryRepository extends JpaRepository<Entry, Long> {
    @Query("SELECT e FROM Entry e WHERE " +
            "lower(e.account.name) like LOWER(concat('%',:accountName,'%') ) " +
            "OR lower(e.account.code) like lower(concat('%',:AccountNumber,'%')) " +
            "OR lower(e.transaction.reference) like lower(concat('%', :transactionNumber, '%') )" +
            "OR lower(e.transaction.description) like lower(concat('%', :TransactionDescription, '%') )")
    List<Entry> searchEntries(String accountName, String AccountNumber, String transactionNumber, String TransactionDescription);
}
