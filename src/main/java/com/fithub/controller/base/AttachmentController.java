package com.fithub.controller.base;

import com.fithub.dto.base.AttachmentDTO;
import com.fithub.exception.ResourceNotFoundException;
import com.fithub.service.base.AttachmentService;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.Base64;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
public class AttachmentController {
    @Autowired
    private AttachmentService attachmentService;
    private final Path fileStorageLocation;

    public AttachmentController() {
        this.fileStorageLocation = Paths.get("files").toAbsolutePath().normalize();
    }

    @PostMapping("/attachment/content")
    public AttachmentDTO storeAttachmentByContent(@RequestParam("attachment") MultipartFile attachment) throws IOException {
        return attachmentService.storeAttachmentByContent(attachment);
    }

    @PostMapping("/attachment/url")
    public AttachmentDTO storeAttachmentByUrl(@RequestBody AttachmentDTO attachmentDTO){
        return attachmentService.storeAttachmentByUrl(attachmentDTO);
    }

    @GetMapping("/attachments")
    public List<AttachmentDTO> getAttachments(){
        return attachmentService.getAttachments();
    }

    @GetMapping("/files/{fileName:.+}")
    public byte[] downloadFile(@PathVariable String fileName) {
        try {
            // Resolve the file path
            Path filePath = fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            // Check if the file exists and is readable
            if (resource.exists() || resource.isReadable()) {
                // Set the response headers
                Tika tika = new Tika();
                HttpHeaders headers = new HttpHeaders();
                headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"");
                headers.add(HttpHeaders.CONTENT_TYPE, tika.detect(resource.getFile().toPath()));
                headers.add(HttpHeaders.CONTENT_LENGTH, String.valueOf(resource.contentLength()));

                // Read the file and return it
                return resource.getInputStream().readAllBytes();
            } else {
                throw new ResourceNotFoundException("File not found");
            }
        } catch (MalformedURLException ex) {
            throw new ResourceNotFoundException("File not found");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
