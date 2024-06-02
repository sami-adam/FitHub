package com.fithub.dto.subscription;

import com.fithub.model.subscription.SubscriptionPeriod;
import lombok.Data;


@Data
public class SubscriptionDTO {

    private String name;
    private SubscriptionPlanDTO plan;
    private SubscriptionPeriod periodType;
    private double price;
}
