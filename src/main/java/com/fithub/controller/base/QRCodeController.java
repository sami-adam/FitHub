package com.fithub.controller.base;

import com.fithub.service.base.QRCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
public class QRCodeController {
    @Autowired
    private QRCodeService qrCodeService;

    @GetMapping(value = "/qr-code", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] generateQRCode(@RequestParam String text, @RequestParam int width, @RequestParam int height) {
        try {
            return qrCodeService.generateQRCode(text, width, height);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
