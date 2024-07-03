package com.fithub.service.subscription;

import com.fithub.dto.subscription.SubscriptionDTO;
import com.fithub.repository.subscription.SubscriptionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SubscriptionServiceImpl implements SubscriptionService{
    private final SubscriptionRepository subscriptionRepository;
    private final ModelMapper mapper;

    @Autowired
    public SubscriptionServiceImpl(SubscriptionRepository subscriptionRepository){
        this.subscriptionRepository = subscriptionRepository;
        this.mapper = new ModelMapper();
    }
    @Override
    public List<SubscriptionDTO> getSubscriptions() {
        List<SubscriptionDTO> subscriptionDTOS = new ArrayList<>();
        subscriptionRepository.findAll().stream().forEach(subscription -> subscriptionDTOS.add(mapper.map(subscription, SubscriptionDTO.class)));
        return subscriptionDTOS;
    }
}
