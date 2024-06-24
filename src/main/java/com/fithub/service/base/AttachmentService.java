package com.fithub.service.base;

import com.fithub.dto.base.AttachmentDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface AttachmentService {
    AttachmentDTO storeAttachmentByContent(MultipartFile attachment) throws IOException;
    AttachmentDTO storeAttachmentByUrl(AttachmentDTO attachmentDTO);
}
