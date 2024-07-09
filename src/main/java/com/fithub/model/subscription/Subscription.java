package com.fithub.model.subscription;

import com.fithub.model.base.BaseEntity;
import com.fithub.model.member.Member;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Table(name = "subscriptions")
@Data
public class Subscription extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @JoinColumn(name = "start_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date startDate;

    @JoinColumn(name = "end_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date endDate;
    // Membership Amounts
    @JoinColumn(name = "subscription_unit_price")
    private double subscriptionUnitPrice;
    @JoinColumn(name = "subscription_qty")
    private double subscriptionQty;
    @JoinColumn(name = "total_amount")
    private double totalAmount;
    @JoinColumn(name = "discount_amount")
    private double discountAmount;
    @JoinColumn(name = "net_amount")
    private double netAmount;

    private SubscriptionStatus status;

}
