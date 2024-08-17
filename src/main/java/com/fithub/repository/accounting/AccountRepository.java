package com.fithub.repository.accounting;

import com.fithub.model.accounting.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> findByCodeContainingIgnoreCaseOrNameContainingIgnoreCaseOrTypeEquals(String code, String name, Account.Type type);
}
