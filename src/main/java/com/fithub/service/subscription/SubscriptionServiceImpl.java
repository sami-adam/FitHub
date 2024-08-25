package com.fithub.service.subscription;

import com.fithub.dto.subscription.SubscriptionDTO;
import com.fithub.exception.ResourceNotFoundException;
import com.fithub.model.member.Member;
import com.fithub.model.product.Product;
import com.fithub.model.subscription.Subscription;
import com.fithub.model.subscription.SubscriptionStatus;
import com.fithub.repository.base.TaxRepository;
import com.fithub.repository.member.MemberRepository;
import com.fithub.repository.product.ProductRepository;
import com.fithub.repository.subscription.SubscriptionRepository;
import com.fithub.service.user.JWTService;
import com.fithub.service.user.UserService;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.*;
import java.util.logging.Logger;

@Service
@Data
public class SubscriptionServiceImpl implements SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;
    private final TaxRepository taxRepository;
    private final ModelMapper mapper;
    private final Logger logger = Logger.getLogger(SubscriptionServiceImpl.class.getName());

    @Autowired
    private UserService usersService;

    @Autowired
    private JWTService jwtUtil;

    @Autowired
    public SubscriptionServiceImpl(SubscriptionRepository subscriptionRepository, MemberRepository memberRepository, ProductRepository productRepository, TaxRepository taxRepository) {
        this.subscriptionRepository = subscriptionRepository;
        this.memberRepository = memberRepository;
        this.productRepository = productRepository;
        this.taxRepository = taxRepository;
        this.mapper = new ModelMapper();
    }

    // Get All Memberships
    public List<SubscriptionDTO> getSubscriptions(){
        List<Subscription> subscriptions = subscriptionRepository.findAll().stream().toList();
        return subscriptions.stream().map(subscription -> mapper.map(subscription, SubscriptionDTO.class)).toList();
    }

    @Override
    public SubscriptionDTO getSubscription(Long id) {
        Subscription subscription = subscriptionRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Subscription not found")
        );
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
        List<SubscriptionStatus> statuses = Arrays.stream(SubscriptionStatus.values()).toList();
        Subscription subscription = subscriptionRepository.findById(id).orElseThrow();
        if (subscriptionDTO.getMember() != null && subscriptionDTO.getMember().getId() != null) {
            Member member = memberRepository.findById(subscriptionDTO.getMember().getId()).orElseThrow();
            subscription.setMember(member);
        }
        if(subscriptionDTO.getProduct() != null && subscriptionDTO.getProduct().getId() != null){
            Product product = productRepository.findById(subscriptionDTO.getProduct().getId()).orElseThrow();
            subscription.setProduct(product);
        }
        if(subscriptionDTO.getTax() != null && subscriptionDTO.getTax().getId() != null){
            subscription.setTax(taxRepository.findById(subscriptionDTO.getTax().getId()).orElseThrow());
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
        if(subscriptionDTO.getDiscountAmount() >= 0){
            subscription.setDiscountAmount(subscriptionDTO.getDiscountAmount());
        }
        if(subscriptionDTO.getStatus() !=null && subscriptionDTO.getStatus().ordinal() > 0 && subscriptionDTO.getStatus().ordinal() < statuses.size()){
            subscription.setStatus(statuses.get(subscriptionDTO.getStatus().ordinal()));
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
        System.out.println(keyword);
        SubscriptionStatus status;
        try{
            status = SubscriptionStatus.valueOf(keyword.toUpperCase());
        } catch (Exception e){
            status = null;
        }
        List<Subscription> subscriptions = subscriptionRepository.searchByKeyword(keyword, keyword, keyword, keyword, status, keyword);
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

    public void checkSubscriptionStatus(String token){
        logger.info("Checking subscription status");
        List<Subscription> subscriptions = subscriptionRepository.findAll();
        for(Subscription subscription : subscriptions){
            if(subscription.getEndDate().before(new Date(System.currentTimeMillis()))){
                subscription.setStatus(SubscriptionStatus.EXPIRED);
                subscriptionRepository.save(subscription);
            }
            if(new Date(System.currentTimeMillis()).after(subscription.getStartDate()) && new Date(System.currentTimeMillis()).before(subscription.getEndDate())){
                subscription.setStatus(SubscriptionStatus.ACTIVE);
                subscriptionRepository.save(subscription);
            }
            // Update Reference
            if(subscription.getReference() == null || subscription.getReference().length() != 9){
                subscription.setReference("SUB" + String.format("%06d", subscription.getId()));
                subscriptionRepository.save(subscription);

            }
        }
    }

    @Scheduled(fixedRate = 1000 * 60 * 30) // Adjust the fixedRate as needed
    public void performTask() {
        UserDetails userDetails = usersService.userDetailsService().loadUserByUsername("admin@fithub.com"); // Replace with actual username
        String jwtToken = jwtUtil.generateToken(userDetails);
        UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(auth);
        SecurityContextHolder.setContext(context);

        try {
            checkSubscriptionStatus(jwtToken);
        } finally {
            SecurityContextHolder.clearContext(); // Clear context after task
        }
    }

}
