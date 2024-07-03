package com.fithub.controller.subscription;

import com.fithub.dto.subscription.SubscriptionDTO;
import com.fithub.service.subscription.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1")public class SubscriptionController {
    private final SubscriptionService subscriptionService;
    @Autowired
    public SubscriptionController(SubscriptionService subscriptionService){
        this.subscriptionService = subscriptionService;
    }

    @GetMapping("/subscriptions")
    public ResponseEntity<List<SubscriptionDTO>> getSubscriptions(){
        return new ResponseEntity<>(subscriptionService.getSubscriptions(), HttpStatus.OK);
    }


}
