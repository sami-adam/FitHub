package com.fithub.service.subscription;

import com.fithub.dto.subscription.SubscriptionDTO;

import java.util.List;
import java.util.Map;

public interface SubscriptionService {
    List<SubscriptionDTO> getSubscriptions();
    SubscriptionDTO getSubscription(Long id);
    SubscriptionDTO addSubscription(SubscriptionDTO subscriptionDTO);
    SubscriptionDTO updateSubscription(SubscriptionDTO subscriptionDTO, Long id);
    Map<String, String> deleteSubscription(Long id);
    List<SubscriptionDTO> searchSubscription(String keyword);
    String changeStatus(Long id);
    SubscriptionDTO generateAccountTransaction(Long id);
}
