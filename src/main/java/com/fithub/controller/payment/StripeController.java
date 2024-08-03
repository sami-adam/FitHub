package com.fithub.controller.payment;
import com.fithub.service.payment.StripeService;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
public class StripeController {

    @Autowired
    private StripeService stripeService;

    @PostMapping("/stripe/create-checkout-session")
    public Map<String, String> createCheckoutSession(@RequestParam Long amount) {
        String successUrl = "http://localhost:8080/home";
        String cancelUrl = "http://your-domain.com/cancel";
        try {
            Session session = stripeService.createCheckoutSession(successUrl, cancelUrl, amount, "usd");
            Map<String, String> responseData = new HashMap<>();
            responseData.put("id", session.getId());
            return responseData;
        } catch (StripeException e) {
            e.printStackTrace();
            throw new RuntimeException("Stripe session creation failed");
        }
    }

    @GetMapping("/stripe/public-key")
    public Map<String, String> getPublicKey() {
        Map<String, String> responseData = new HashMap<>();
        responseData.put("publicKey", stripeService.getPublicKey());
        return responseData;
    }
}
