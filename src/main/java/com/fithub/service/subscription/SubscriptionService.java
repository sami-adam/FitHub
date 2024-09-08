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
    Page<SubscriptionDTO> searchSubscription(Pageable pageable, String keyword);
    List<SubscriptionDTO> getMemberSubscriptions(Long memberId);
    String changeStatus(Long id);
    SubscriptionDTO generateAccountTransaction(Long id);
    List<SubscriptionDTO> getSubscriptionsByProduct(Long productId);
    List<SubscriptionDTO> getSubscriptionsByCategory(Long productCategoryId);
    List<SubscriptionDTO> getSubscriptionsByYear(int year);
    List<SubscriptionDTO> getSubscriptionsByYearAndMonth(int year, int month);
    List<SubscriptionDTO> getSubscriptionsByYearMonthAndDay(int year, int month, int day);
    List<SubscriptionDTO> getSubscriptionsByStartDateAndEndDate(String startDate, String endDate);
}
