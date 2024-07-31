package com.fithub.controller.subscription;

import com.fithub.service.subscription.SubscriptionReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class SubscriptionReportController {
    private final SubscriptionReportService subscriptionReportService;

    @GetMapping("/subscriptions/excel/{subscriptionIds}")
    public ResponseEntity<byte[]> printExcel(@PathVariable("subscriptionIds") List<Long> subscriptionIds) throws IOException {
        return new ResponseEntity<>(subscriptionReportService.generateReport(subscriptionIds), HttpStatus.OK);
    }
}
