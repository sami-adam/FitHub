package com.fithub.service.base;

import com.fithub.repository.base.EmailRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private final EmailRepository emailRepository;
    private final ModelMapper mapper;

    @Autowired
    public EmailService(EmailRepository emailRepository){
        this.emailRepository = emailRepository;
        this.mapper = new ModelMapper();
    }


//    @Autowired
//    private JavaMailSender mailSender;
//
//    public void sendEmail(String to, String subject, String htmlBody, MultipartFile[] attachments) throws MessagingException, IOException {
//        MimeMessage message = mailSender.createMimeMessage();
//        MimeMessageHelper helper = new MimeMessageHelper(message, true);
//
//        helper.setTo(to);
//        helper.setSubject(subject);
//        helper.setText(htmlBody, true);
//
//        if (attachments != null) {
//            for (MultipartFile attachment : attachments) {
//                InputStreamSource source = new ByteArrayResource(attachment.getBytes());
//                helper.addAttachment(attachment.getOriginalFilename(), source);
//            }
//        }
//
//        mailSender.send(message);
//    }
}
