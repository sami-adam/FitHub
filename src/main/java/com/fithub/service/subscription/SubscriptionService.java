package com.fithub.service.subscription;

import com.fithub.dto.subscription.SubscriptionDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface SubscriptionService {
    Page<SubscriptionDTO> getSubscriptions(Pageable pageable);
    SubscriptionDTO getSubscription(Long id);
    SubscriptionDTO addSubscription(SubscriptionDTO subscriptionDTO);
    SubscriptionDTO updateSubscription(SubscriptionDTO subscriptionDTO, Long id);
    Map<String, String> deleteSubscription(Long id);
    List<SubscriptionDTO> searchSubscription(String keyword);
    String changeStatus(Long id);
    SubscriptionDTO generateAccountTransaction(Long id);
}
