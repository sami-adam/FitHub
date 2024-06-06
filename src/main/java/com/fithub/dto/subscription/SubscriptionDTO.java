package com.fithub.dto.subscription;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fithub.model.subscription.SubscriptionPeriod;
import lombok.Data;


@Data
public class SubscriptionDTO {
    private Long id;
    private String name;
    private SubscriptionPlanDTO plan;
    private SubscriptionPeriod periodType;
    private double price;
}
