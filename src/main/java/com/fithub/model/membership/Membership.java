package com.fithub.model.membership;

import com.fithub.model.member.Member;
import com.fithub.model.subscription.Subscription;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Table(name = "memberships")
@Data
public class Membership {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
    @ManyToOne
    @JoinColumn(name = "subscription_id")
    private Subscription subscription;
    @JoinColumn(name = "start_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date startDate;
    @JoinColumn(name = "end_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date endDate;
    // Membership Amounts
    @JoinColumn(name = "subscription_price")
    private double subscriptionPrice;
    @JoinColumn(name = "subscription_qty")
    private double subscriptionQty;
    @JoinColumn(name = "total_amount")
    private double totalAmount;
    @JoinColumn(name = "discount_amount")
    private double discountAmount;
    @JoinColumn(name = "net_amount")
    private double netAmount;

    private MembershipStatus status;

}
