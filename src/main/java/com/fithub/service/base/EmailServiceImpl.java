package com.fithub.service.base;

import com.fithub.dto.base.AttachmentDTO;
import com.fithub.dto.base.EmailDTO;
import com.fithub.model.base.Attachment;
import com.fithub.model.base.Email;
import com.fithub.model.base.EmailStatus;
import com.fithub.repository.base.AttachmentRepository;
import com.fithub.repository.base.EmailRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class EmailServiceImpl implements EmailService{
    private final EmailRepository emailRepository;
    private final ModelMapper mapper;
    private final JavaMailSender mailSender;
    private final AttachmentRepository attachmentRepository;

    @Autowired
    public EmailServiceImpl(EmailRepository emailRepository, JavaMailSender mailSender, AttachmentRepository attachmentRepository){
        this.emailRepository = emailRepository;
        this.attachmentRepository = attachmentRepository;
        this.mapper = new ModelMapper();
        this.mailSender = mailSender;
    }

    public String sendEmail(EmailDTO emailDTO) throws MessagingException, IOException {
        Email email = mapper.map(emailDTO, Email.class);
        email.setId(null);
        // Sending Email
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(emailDTO.getEmailTo());
        helper.setSubject(emailDTO.getSubject());
        helper.setText(emailDTO.getEmailBody(), true);
        if(emailDTO.getAttachments() != null){
            for(AttachmentDTO attachment: emailDTO.getAttachments()){
                InputStreamSource source = new ByteArrayResource(attachment.getData());
                helper.addAttachment(Objects.requireNonNull(attachment.getName()), source);
            }
        }
        Email savedEmail;
        String emailStatus = "";
        try {
            mailSender.send(message);
            email.setStatus(EmailStatus.SENT);
            savedEmail = emailRepository.save(email);
            emailStatus = "Mail sent Successfully";
        } catch (Exception e){
            email.setStatus(EmailStatus.FAILED);
            savedEmail = emailRepository.save(email);
            emailStatus = "Mail failed to send " + e;
        }

        // Store Attachment
        for(AttachmentDTO attachmentDTO: emailDTO.getAttachments()){
            attachmentDTO.setId(null);
            Attachment attachment = mapper.map(attachmentDTO, Attachment.class);
            attachment.setEmail(savedEmail);
            attachmentRepository.save(attachment);
        }
        return emailStatus;
    }

    public List<EmailDTO> getEmails() {
        List<EmailDTO> emailDTOS = new ArrayList<>();
        for(Email email: emailRepository.findAll()){
            emailDTOS.add(mapper.map(email, EmailDTO.class));
        }
        return emailDTOS;
    }
}
