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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;

@Service
public class EmailService {
    private final EmailRepository emailRepository;
    private final ModelMapper mapper;
    private final JavaMailSender mailSender;
    private final AttachmentRepository attachmentRepository;

    @Autowired
    public EmailService(EmailRepository emailRepository, JavaMailSender mailSender, AttachmentRepository attachmentRepository){
        this.emailRepository = emailRepository;
        this.attachmentRepository = attachmentRepository;
        this.mapper = new ModelMapper();
        this.mailSender = mailSender;
    }

    public void sendEmail(EmailDTO emailDTO) throws MessagingException, IOException {
        Email email = mapper.map(emailDTO, Email.class);
        email.setId(null);
        // Sending Email
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(emailDTO.getEmailTo());
        helper.setSubject(emailDTO.getSubject());
        helper.setText(emailDTO.getEmailBody(), true);
        for(AttachmentDTO attachmentDTO: emailDTO.getAttachments()){
            attachmentDTO.setId(null);
            attachmentRepository.save(mapper.map(attachmentDTO, Attachment.class));
        }

        if(emailDTO.getAttachments() != null){
            for(AttachmentDTO attachment: emailDTO.getAttachments()){
                InputStreamSource source = new ByteArrayResource(attachment.getData());
                helper.addAttachment(Objects.requireNonNull(attachment.getName()), source);
            }
        }
        try {
            mailSender.send(message);
            email.setStatus(EmailStatus.SENT);
            emailRepository.save(email);
        } catch (Exception e){
            email.setStatus(EmailStatus.FAILED);
            emailRepository.save(email);
        }

    }
}
