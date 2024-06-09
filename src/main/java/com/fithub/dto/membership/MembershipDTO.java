package com.fithub.dto.membership;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fithub.dto.member.MemberDTO;
import com.fithub.dto.subscription.SubscriptionDTO;
import com.fithub.model.membership.MembershipStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;

@Data
public class MembershipDTO {
    private Long id;
    @JsonIgnoreProperties({"memberShips"})
    private MemberDTO member;
    private SubscriptionDTO subscription;
    @NotNull(message = "Start date cannot be null")
    private Date startDate;
    @NotNull(message = "End date cannot be null")
    private Date endDate;
    // Membership Amounts
    private double subscriptionUnitPrice;
    private double subscriptionQty;
    private double totalAmount;
    private double discountAmount;
    private double netAmount;

    private MembershipStatus status;

}
