package com.fithub.service.base;

import com.fithub.dto.base.EmailDTO;
import jakarta.mail.MessagingException;

import java.io.IOException;
import java.util.List;

public interface EmailService {
    List<EmailDTO> getEmails();
    String sendEmail(EmailDTO emailDTO) throws MessagingException, IOException;
}
