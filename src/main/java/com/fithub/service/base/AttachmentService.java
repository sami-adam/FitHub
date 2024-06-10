package com.fithub.service.base;
import com.fithub.model.base.Attachment;
import com.fithub.repository.base.AttachmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@Service
public class AttachmentService {
    private AttachmentRepository attachmentRepository;

    @Autowired
    public void setAttachmentRepository(AttachmentRepository attachmentRepository){
        this.attachmentRepository = attachmentRepository;
    }

    public Attachment storeAttachmentByContent(MultipartFile file) throws IOException {
        Attachment attachment = new Attachment();
        attachment.setName(file.getOriginalFilename());
        attachment.setType(file.getContentType());
        attachment.setData(file.getBytes());
        return attachmentRepository.save(attachment);
    }

    public Attachment getAttachment(Long id){
        return attachmentRepository.findById(id).orElse(null);
    }
}
