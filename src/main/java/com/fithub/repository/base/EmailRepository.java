package com.fithub.repository.base;

import com.fithub.model.base.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmailRepository extends JpaRepository<Email, Long> {
    List<Email> findBySubjectContainingIgnoreCaseOrEmailFromContainingIgnoreCaseOrEmailToContainingIgnoreCase(String subject, String emailFrom, String emailTo);
}
