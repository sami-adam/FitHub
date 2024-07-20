package com.fithub.model.subscription;

import com.fithub.model.base.BaseEntity;
import com.fithub.model.base.Tax;
import com.fithub.model.member.Member;
import com.fithub.model.product.Product;
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

    private String reference;

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
    @JoinColumn(name = "product_id")
    @ManyToOne
    private Product product;
    @JoinColumn(name = "subscription_unit_price")
    private double subscriptionUnitPrice;
    @JoinColumn(name = "subscription_qty")
    private double subscriptionQty;
    @JoinColumn(name = "total_amount")
    private double totalAmount;
    @JoinColumn(name = "discount_amount")
    private double discountAmount;

    @Column(name = "tax_amount")
    private Double taxAmount;

    @JoinColumn(name = "net_amount")
    private double netAmount;

    @ManyToOne
    @JoinColumn(name = "tax_id")
    private Tax tax;

    private SubscriptionStatus status;


    @PostPersist
    public void postPersist() {
        reference = "SUB" + String.format("%06d", id);
    }

    public double getSubscriptionUnitPrice() {
        return (double) product.getPrice();
    }
    public double getTotalAmount() {
        return subscriptionUnitPrice * subscriptionQty;
    }

    public double getNetAmount() {
        return (getTotalAmount() - discountAmount);
    }

    public double getTaxAmount() {
        if(tax != null){
            return getTotalAmount() * tax.getRate()/100;
        }
        return 0.0;
    }

}
