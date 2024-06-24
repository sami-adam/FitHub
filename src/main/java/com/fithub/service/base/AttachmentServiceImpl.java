package com.fithub.service.base;
import com.fithub.dto.base.AttachmentDTO;
import com.fithub.model.base.Attachment;
import com.fithub.repository.base.AttachmentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@Service
public class AttachmentServiceImpl implements AttachmentService{
    private final AttachmentRepository attachmentRepository;
    private final ModelMapper mapper;

    @Autowired
    public AttachmentServiceImpl(AttachmentRepository attachmentRepository){
        this.attachmentRepository = attachmentRepository;
        this.mapper = new ModelMapper();
    }

    public AttachmentDTO storeAttachmentByContent(MultipartFile file) throws IOException {
        Attachment attachment = new Attachment();
        attachment.setName(file.getOriginalFilename());
        attachment.setType(file.getContentType());
        attachment.setData(file.getBytes());
        Attachment saved = attachmentRepository.save(attachment);
        return mapper.map(saved, AttachmentDTO.class);
    }

    public AttachmentDTO storeAttachmentByUrl(AttachmentDTO attachmentDTO){
        attachmentRepository.save(mapper.map(attachmentDTO, Attachment.class));
        return attachmentDTO;
    }

    public Attachment getAttachment(Long id){
        return attachmentRepository.findById(id).orElse(null);
    }
}
