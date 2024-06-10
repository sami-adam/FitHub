package com.fithub.dto.base;

import com.fithub.model.base.AttachmentType;


public class AttachmentDTO {
    private Long id;
    private String name;
    private AttachmentType attachmentType;
    private String type;
    private byte[] data;
    private String url;
}
