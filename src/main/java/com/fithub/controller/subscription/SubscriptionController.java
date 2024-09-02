package com.fithub.controller.subscription;

import com.fithub.dto.subscription.SubscriptionDTO;
import com.fithub.service.subscription.SubscriptionService;
import lombok.Data;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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
    public ResponseEntity<Page<SubscriptionDTO>> getSubscriptions(Pageable pageable){
        return new ResponseEntity<>(subscriptionService.getSubscriptions(pageable), HttpStatus.OK);
    }

    @GetMapping("/subscription/{id}")
    public ResponseEntity<SubscriptionDTO> getSubscription(@PathVariable("id") Optional<Object> id){
        if(id.isPresent()){
            if(id.get() instanceof Long){
                return new ResponseEntity<>(subscriptionService.getSubscription((Long) id.get()), HttpStatus.OK);
            }else if(id.get() instanceof String){
                return new ResponseEntity<>(null, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
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
    public ResponseEntity<Page<SubscriptionDTO>> searchSubscription(Pageable pageable, @PathVariable("keyword") String keyword){
        return new ResponseEntity<>(subscriptionService.searchSubscription(pageable, keyword), HttpStatus.OK);
    }

    @PutMapping("/subscription/status/{id}")
    public ResponseEntity<String> changeStatus(@PathVariable Long id){
        return new ResponseEntity<>(subscriptionService.changeStatus(id), HttpStatus.OK);
    }

    @PostMapping("/subscription/account-transaction/{id}")
    public ResponseEntity<SubscriptionDTO> generateAccountTransaction(@PathVariable Long id){
        return new ResponseEntity<>(subscriptionService.generateAccountTransaction(id), HttpStatus.OK);
    }
}
