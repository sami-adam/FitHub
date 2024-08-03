package com.fithub.service.payment;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Data
public class StripeService {

    @Value("${stripe.api.key}")
    private String apiKey;

    @Value("${stripe.public.key}")
    private String publicKey;

    @PostConstruct
    public void init() {
        Stripe.apiKey = apiKey;
    }

    public Session createCheckoutSession(String successUrl, String cancelUrl, long amount, String currency) throws StripeException {
        SessionCreateParams params = SessionCreateParams.builder()
            .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
            .setMode(SessionCreateParams.Mode.PAYMENT)
            .setSuccessUrl(successUrl)
            .setCancelUrl(cancelUrl)
            .addLineItem(
                SessionCreateParams.LineItem.builder()
                    .setPriceData(
                        SessionCreateParams.LineItem.PriceData.builder()
                            .setCurrency(currency)
                            .setUnitAmount(amount)
                            .setProductData(
                                SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                    .setName("Sample Product")
                                    .build()
                            )
                            .build()
                    )
                    .setQuantity(1L)
                    .build()
            )
            .build();

        return Session.create(params);
    }

}
