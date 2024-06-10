package com.fithub.model.base;
import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "emails")
@Data
public class Email extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String subject;
    @Column(name = "email_from")
    private String emailFrom;

    @Column(name = "email_to")
    private String emailTo;

    @Column(name = "email_cc")
    private String emailCc;

    @Column(name = "reply_to")
    private String replyTo;

    @Column(name = "scheduled_date")
    private Date scheduledDate;

    @Column(name = "email_body")
    private String emailBody;

    @OneToMany
    @JoinColumn(name = "attachment_ids")
    private List<Attachment> attachments;

    private EmailStatus status;
}
