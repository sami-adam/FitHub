package com.fithub.controller.base;

import com.fithub.dto.base.AttachmentDTO;
import com.fithub.model.base.ResponseModel;
import com.fithub.service.base.AttachmentService;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.io.IOException;
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
    public ResponseModel<Base64> downloadFile(@PathVariable String fileName) {
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

                // Read the file and encode it as a Base64 string
                byte[] fileContent = resource.getInputStream().readAllBytes();
                String base64Content = Base64.getEncoder().encodeToString(fileContent);

                // Return the Base64 string in the response
                return new ResponseModel<>(true, "data:" +tika.detect(fileContent)+";" +"base64," + base64Content, "File found");
            } else {
                return new ResponseModel<>(false, null, "File not found");
            }
        } catch (MalformedURLException ex) {
            return new ResponseModel<>(false, null, "File not found");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
