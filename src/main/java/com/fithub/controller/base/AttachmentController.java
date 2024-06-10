package com.fithub.controller.base;

import com.fithub.dto.base.AttachmentDTO;
import com.fithub.service.base.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1")
public class AttachmentController {
    @Autowired
    private AttachmentService attachmentService;

    @PostMapping("/attachment/content")
    public AttachmentDTO storeAttachmentByContent(@RequestParam("attachment") MultipartFile attachment) throws IOException {
        return attachmentService.storeAttachmentByContent(attachment);
    }

    @PostMapping("/attachment/url")
    public AttachmentDTO storeAttachmentByUrl(@RequestBody AttachmentDTO attachmentDTO){
        return attachmentService.storeAttachmentByUrl(attachmentDTO);
    }

}
