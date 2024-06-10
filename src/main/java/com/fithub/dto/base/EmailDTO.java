package com.fithub.dto.base;
import com.fithub.model.base.EmailStatus;
import java.util.Date;
import java.util.List;

public class EmailDTO {
    private Long id;
    private String subject;
    private String emailFrom;
    private String emailTo;
    private String emailCc;
    private String replyTo;
    private Date scheduledDate;
    private String emailBody;
    private List<AttachmentDTO> attachments;
    private EmailStatus status;
}
