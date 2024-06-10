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
}
