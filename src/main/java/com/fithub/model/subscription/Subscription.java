package com.fithub.model.subscription;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "subscription")
@Data
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToOne
    @JoinColumn(name = "plan_id")
    private SubscriptionPlan plan;
    @JoinColumn(name = "period_type")
    private SubscriptionPeriod periodType;
    private double price;
}
