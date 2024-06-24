package com.fithub.controller.base;

import com.fithub.dto.base.AttachmentDTO;
import com.fithub.dto.base.EmailDTO;
import com.fithub.model.base.AttachmentType;
import com.fithub.model.base.EmailStatus;
import com.fithub.service.base.EmailService;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class EmailController {
    private final EmailService emailService;
    @Autowired
    public EmailController(EmailService emailService){
        this.emailService = emailService;
    }

    @GetMapping("/emails")
    public ResponseEntity<List<EmailDTO>> getEmails(){
        return new ResponseEntity<>(emailService.getEmails(), HttpStatus.OK);
    }

    @PostMapping("/email")
    public ResponseEntity<String> sendEmail(@RequestParam("request") String request, @RequestParam("attachment") MultipartFile attachment) throws MessagingException, IOException {
        JsonObject json = (JsonObject) JsonParser.parseString(request);
        List<AttachmentDTO> attachments = new ArrayList<>();
        AttachmentDTO attachmentDTO = new AttachmentDTO();
        if(attachment != null){
            attachmentDTO.setAttachmentType(AttachmentType.FILE);
            attachmentDTO.setName(attachment.getOriginalFilename());
            attachmentDTO.setData(attachment.getBytes());
            attachmentDTO.setType(attachment.getContentType());
        }
        attachments.add(attachmentDTO);
        EmailDTO emailDTO = new EmailDTO(1L,
                json.get("subject").getAsString(),
                json.get("emailFrom").getAsString(),
                json.get("emailTo").getAsString(),
                json.get("emailCc").getAsString(),
                json.get("replyTo").getAsString(),
                new Date(System.currentTimeMillis()),
                json.get("emailBody").getAsString(),
                attachments,
                EmailStatus.OUTGOING);
        emailService.sendEmail(emailDTO);
        return new ResponseEntity<>("Mail Sent Successfully", HttpStatus.OK);
    }
}
