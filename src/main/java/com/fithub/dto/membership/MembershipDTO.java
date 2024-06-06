package com.fithub.dto.membership;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fithub.dto.member.MemberDTO;
import com.fithub.dto.subscription.SubscriptionDTO;
import com.fithub.model.membership.MembershipStatus;
import lombok.Data;

import java.util.Date;

@Data
public class MembershipDTO {
    private Long id;
    @JsonIgnoreProperties({"memberShips"})
    private MemberDTO member;
    private SubscriptionDTO subscription;
    private Date startDate;
    private Date endDate;
    // Membership Amounts
    private double subscriptionUnitPrice;
    private double subscriptionQty;
    private double totalAmount;
    private double discountAmount;
    private double netAmount;

    private MembershipStatus status;

}
