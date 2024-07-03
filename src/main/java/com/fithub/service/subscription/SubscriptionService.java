package com.fithub.service.subscription;

import com.fithub.dto.subscription.SubscriptionDTO;

import java.util.List;

public interface SubscriptionService {
    List<SubscriptionDTO> getSubscriptions();
}
