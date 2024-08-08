package com.fithub.model.base;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "attachments")
@Data
public class Attachment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    @Column(name = "attachment_type")
    private AttachmentType attachmentType;
    private String type;
    private byte[] data;
    private String url;

    @ManyToOne
    @JoinColumn(name = "email_id")
    private Email email;
}
