package com.fithub.service.subscription;

import com.fithub.dto.subscription.SubscriptionDTO;
import com.fithub.model.member.Member;
import com.fithub.model.subscription.Subscription;
import com.fithub.model.subscription.SubscriptionStatus;
import com.fithub.repository.member.MemberRepository;
import com.fithub.repository.subscription.SubscriptionRepository;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Data
public class SubscriptionServiceImpl implements SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;
    private final MemberRepository memberRepository;
    private final ModelMapper mapper;
    @Autowired
    public SubscriptionServiceImpl(SubscriptionRepository subscriptionRepository, MemberRepository memberRepository){
        this.subscriptionRepository = subscriptionRepository;
        this.memberRepository = memberRepository;
        this.mapper = new ModelMapper();
    }

    // Get All Memberships
    public List<SubscriptionDTO> getSubscriptions(){
        List<Subscription> subscriptions = subscriptionRepository.findAll().stream().toList();
        return subscriptions.stream().map(subscription -> mapper.map(subscription, SubscriptionDTO.class)).toList();
    }

    @Override
    public SubscriptionDTO getSubscription(Long id) {
        Subscription subscription = subscriptionRepository.findById(id).orElseThrow();
        if(subscription.getId() > 0){
            return mapper.map(subscription, SubscriptionDTO.class);
        }
        return null;
    }

    // Add New Membership
    public SubscriptionDTO addSubscription(SubscriptionDTO subscriptionDTO) {
        System.out.println(subscriptionDTO);
        subscriptionRepository.save(mapper.map(subscriptionDTO, Subscription.class));
        return subscriptionDTO;
    }

    // Update Membership
    public SubscriptionDTO updateSubscription(SubscriptionDTO subscriptionDTO, Long id) {
        Subscription subscription = subscriptionRepository.findById(id).orElseThrow();
        if (subscriptionDTO.getMember() != null) {
            Member member = memberRepository.findById(subscriptionDTO.getMember().getId()).orElseThrow();
            subscription.setMember(member);
        }
        if(subscriptionDTO.getStartDate() != null){
            subscription.setStartDate(subscriptionDTO.getStartDate());
        }
        if(subscriptionDTO.getEndDate() != null){
            subscription.setEndDate(subscriptionDTO.getEndDate());
        }
        if(subscriptionDTO.getSubscriptionUnitPrice() > 0){
            subscription.setSubscriptionUnitPrice(subscriptionDTO.getSubscriptionUnitPrice());
        }
        if(subscriptionDTO.getSubscriptionQty() > 0){
            subscription.setSubscriptionQty(subscriptionDTO.getSubscriptionQty());
        }

        subscriptionRepository.save(subscription);
        return mapper.map(subscription, SubscriptionDTO.class);
    }

    // Delete Membership
    public Map<String, String> deleteSubscription(Long id){
        Map<String, String> response = new HashMap<>();
        subscriptionRepository.deleteById(id);
        response.put("message", "Resource has been deleted");
        response.put("status", "successful");
        return response;
    }

    // Search Memberships
    public List<SubscriptionDTO> searchSubscription(String keyword){
        List<Subscription> subscriptions = subscriptionRepository.searchByKeyword(keyword, keyword);
        return subscriptions.stream().map(subscription -> mapper.map(subscription, SubscriptionDTO.class)).toList();
    }

    @Override
    public String changeStatus(Long id) {
        Subscription subscription = subscriptionRepository.findById(id).orElseThrow();
        if(subscription.getId() > 0){
            List<SubscriptionStatus> statuses = Arrays.stream(SubscriptionStatus.values()).toList();
            try {
                subscription.setStatus(statuses.get(subscription.getStatus().ordinal() + 1));
                subscriptionRepository.save(subscription);
            } catch (Exception e){
                return e.toString();
            }

            return "Status Changed";
        }
        return "Failed To Change Status";
    }

}
