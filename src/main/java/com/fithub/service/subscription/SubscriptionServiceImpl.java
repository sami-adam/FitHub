package com.fithub.service.subscription;

import com.fithub.dto.accounting.AccountDTO;
import com.fithub.dto.accounting.EntryDTO;
import com.fithub.dto.accounting.JournalDTO;
import com.fithub.dto.accounting.TransactionDTO;
import com.fithub.dto.subscription.SubscriptionDTO;
import com.fithub.exception.ResourceNotFoundException;
import com.fithub.model.accounting.Entry;
import com.fithub.model.accounting.Transaction;
import com.fithub.model.member.Member;
import com.fithub.model.product.Product;
import com.fithub.model.subscription.Subscription;
import com.fithub.model.subscription.SubscriptionStatus;
import com.fithub.repository.base.TaxRepository;
import com.fithub.repository.member.MemberRepository;
import com.fithub.repository.product.ProductRepository;
import com.fithub.repository.subscription.SubscriptionRepository;
import com.fithub.service.accounting.JournalService;
import com.fithub.service.accounting.TransactionService;
import com.fithub.service.user.JWTService;
import com.fithub.service.user.UserService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.logging.Logger;

@Service
@Data
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;
    private final TaxRepository taxRepository;
    private final TransactionService transactionService;
    private final JournalService journalService;
    private final Logger logger = Logger.getLogger(SubscriptionServiceImpl.class.getName());
    private final ModelMapper mapper = new ModelMapper();

    @Autowired
    private UserService usersService;

    @Autowired
    private JWTService jwtUtil;

    // Get All Memberships
    public Page<SubscriptionDTO> getSubscriptions(Pageable pageable){
        return subscriptionRepository.findAll(pageable).map(subscription -> mapper.map(subscription, SubscriptionDTO.class));
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
    public Page<SubscriptionDTO> searchSubscription(Pageable pageable, String keyword){
        SubscriptionStatus status;
        try{
            status = SubscriptionStatus.valueOf(keyword.toUpperCase());
        } catch (Exception e){
            status = null;
        }
        return subscriptionRepository.searchByKeyword(pageable, keyword, keyword, keyword, keyword, status, keyword).map(subscription -> mapper.map(subscription, SubscriptionDTO.class));
    }

    @Override
    public List<SubscriptionDTO> getMemberSubscriptions(Long memberId) {
        return subscriptionRepository.findByMemberId(memberId).stream().map(subscription -> mapper.map(subscription, SubscriptionDTO.class)).toList();
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

    @Override
    public SubscriptionDTO generateAccountTransaction(Long id) {
        Subscription subscription = subscriptionRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Subscription not found")
        );
        if(subscription.getTransaction() == null){
            List<JournalDTO> journalDTOS = journalService.searchJournals("Sale");
            TransactionDTO transactionDTO = new TransactionDTO();
            transactionDTO.setTimestamp(LocalDate.now().atStartOfDay());
            transactionDTO.setDescription(String.format("Subscription Payment for %s", subscription.getReference()));
            EntryDTO firstEntry = new EntryDTO();
            firstEntry.setDebit((BigDecimal.valueOf(subscription.getSubscriptionUnitPrice() * subscription.getSubscriptionQty())));
            firstEntry.setType(Entry.Type.DEBIT);
            firstEntry.setAccount(mapper.map(subscription.getProduct().getCategory().getIncomeAccount(), AccountDTO.class));
            firstEntry.setTransaction(transactionDTO);

            EntryDTO secondEntry = new EntryDTO();
            secondEntry.setCredit((BigDecimal.valueOf(subscription.getSubscriptionUnitPrice() * subscription.getSubscriptionQty())));
            secondEntry.setType(Entry.Type.CREDIT);
            secondEntry.setAccount(mapper.map(subscription.getProduct().getCategory().getExpenseAccount(), AccountDTO.class));
            secondEntry.setTransaction(transactionDTO);

            transactionDTO.setEntries(List.of(firstEntry, secondEntry));
            if(!journalDTOS.isEmpty()) {
                transactionDTO.setJournal(journalDTOS.getFirst());
            }
            transactionDTO.setStatus(Transaction.Status.DRAFT);
            TransactionDTO savedTransactionDTO = transactionService.addTransaction(transactionDTO);
            subscription.setTransaction(mapper.map(transactionService.getTransaction(savedTransactionDTO.getId()), Transaction.class));
            subscriptionRepository.save(subscription);
        }
        return null;
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
