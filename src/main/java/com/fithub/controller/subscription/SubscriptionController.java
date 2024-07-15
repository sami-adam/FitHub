package com.fithub.controller.subscription;

import com.fithub.dto.subscription.SubscriptionDTO;
import com.fithub.service.subscription.SubscriptionService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@Data
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
public class SubscriptionController {
    private SubscriptionService subscriptionService;
    @Autowired
    public void setSubscriptionService(SubscriptionService subscriptionService){
        this.subscriptionService = subscriptionService;
    }

    // Get All Subscription
    @GetMapping(path = "/subscriptions", produces = {"Application/json"})
    public ResponseEntity<List<SubscriptionDTO>> getSubscriptions(){
        return new ResponseEntity<>(subscriptionService.getSubscriptions(), HttpStatus.OK);
    }

    @GetMapping("/subscription/{id}")
    public ResponseEntity<SubscriptionDTO> getSubscription(@PathVariable("id") Long id){
        return new ResponseEntity<>(subscriptionService.getSubscription(id), HttpStatus.OK);
    }

    // Add New Subscription
    @PostMapping("subscription")
    public ResponseEntity<SubscriptionDTO> addSubscription(@RequestBody SubscriptionDTO subscriptionDTO){
        return new ResponseEntity<>(subscriptionService.addSubscription(subscriptionDTO), HttpStatus.CREATED);
    }

    // Update Subscription
    @PutMapping("subscription/{id}")
    public ResponseEntity<SubscriptionDTO> updateSubscription(@RequestBody SubscriptionDTO subscriptionDTO, @PathVariable("id") Long id){
        return new ResponseEntity<>(subscriptionService.updateSubscription(subscriptionDTO, id), HttpStatus.OK);
    }

    // Delete Subscription
    @DeleteMapping("subscription/{id}")
    public ResponseEntity<Map<String, String>> deleteSubscription(@PathVariable("id") Long id){
        return new ResponseEntity<>(subscriptionService.deleteSubscription(id), HttpStatus.OK);
    }

    // Search Subscriptions
    @GetMapping("subscriptions/search/{keyword}")
    public ResponseEntity<List<SubscriptionDTO>> searchSubscription(@PathVariable("keyword") String keyword){
        return new ResponseEntity<>(subscriptionService.searchSubscription(keyword), HttpStatus.OK);
    }

    @PutMapping("/subscription/status/{id}")
    public ResponseEntity<String> changeStatus(@PathVariable Long id){
        return new ResponseEntity<>(subscriptionService.changeStatus(id), HttpStatus.OK);
    }
}
