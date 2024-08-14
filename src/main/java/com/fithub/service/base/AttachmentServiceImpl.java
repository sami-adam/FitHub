package com.fithub.service.base;
import com.fithub.config.FileUrlGenerator;
import com.fithub.dto.base.AttachmentDTO;
import com.fithub.model.base.Attachment;
import com.fithub.repository.base.AttachmentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class AttachmentServiceImpl implements AttachmentService{
    private final AttachmentRepository attachmentRepository;
    private final ModelMapper mapper;
    private final Path fileStorageLocation;
    private final FileUrlGenerator fileUrlGenerator; // Inject URL generator

    @Autowired
    public AttachmentServiceImpl(AttachmentRepository attachmentRepository, FileUrlGenerator fileUrlGenerator){
        this.attachmentRepository = attachmentRepository;
        this.fileUrlGenerator = fileUrlGenerator;
        this.mapper = new ModelMapper();
        this.fileStorageLocation = Paths.get("files").toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (IOException ex) {
            throw new RuntimeException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    public AttachmentDTO storeAttachmentByContent(MultipartFile file) throws IOException {
        // Generate a unique file name to avoid collisions
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        Path targetLocation = this.fileStorageLocation.resolve(fileName);

        // Save the file to the specified location
        Files.copy(file.getInputStream(), targetLocation);

        // Create and populate the Attachment entity
        Attachment attachment = new Attachment();
        attachment.setName(file.getOriginalFilename());
        attachment.setType(file.getContentType());
        attachment.setPath(targetLocation.toString()); // Store the file path

        // Generate the URL to access the file
        String fileUrl = fileUrlGenerator.generateFileUrl(fileName);
        attachment.setUrl(fileUrl); // Set the URL in the Attachment entity

        // Save the Attachment entity to the database
        Attachment saved = attachmentRepository.save(attachment);

        // Convert the saved entity to a DTO and return it
        return mapper.map(saved, AttachmentDTO.class);
    }

    public AttachmentDTO storeAttachmentByUrl(AttachmentDTO attachmentDTO){
        attachmentRepository.save(mapper.map(attachmentDTO, Attachment.class));
        return attachmentDTO;
    }

    @Override
    public List<AttachmentDTO> getAttachments() {
        List<AttachmentDTO> attachmentDTOS = new ArrayList<>();
        for(Attachment attachment: attachmentRepository.findAll()){
            attachmentDTOS.add(mapper.map(attachment, AttachmentDTO.class));
        }
        return attachmentDTOS;
    }

    public Attachment getAttachment(Long id){
        return attachmentRepository.findById(id).orElse(null);
    }
}
