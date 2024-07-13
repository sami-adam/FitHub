package com.fithub.dto.subscription;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fithub.dto.member.MemberDTO;
import com.fithub.dto.product.ProductDTO;
import com.fithub.model.subscription.SubscriptionStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;

@Data
public class SubscriptionDTO {
    private Long id;
    @JsonIgnoreProperties({"subscriptions"})
    private MemberDTO member;
    @NotNull(message = "Start date cannot be null")
    private Date startDate;
    @NotNull(message = "End date cannot be null")
    private Date endDate;
    // Membership Amounts
    private ProductDTO product;
    private double subscriptionUnitPrice;
    private double subscriptionQty;
    private double totalAmount;
    private double discountAmount;
    private double netAmount;

    private SubscriptionStatus status;

}
