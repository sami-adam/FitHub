package com.fithub.model.subscription;

import com.fithub.model.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "subscription_plans")
@Data
public class SubscriptionPlan extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
}
