package com.fithub.repository.subscription;

import com.fithub.model.subscription.Subscription;
import com.fithub.model.subscription.SubscriptionStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    @Query("SELECT m FROM Subscription AS m WHERE " +
            "LOWER(m.reference) LIKE LOWER(CONCAT('%', :reference, '%')) OR " +
            "LOWER(m.member.firstName) LIKE LOWER(CONCAT('%', :firstName, '%')) OR " +
            "LOWER(m.member.lastName) LIKE LOWER(CONCAT('%', :lastName, '%')) OR " +
            "LOWER(m.member.identificationNumber) LIKE LOWER(CONCAT('%', :identificationNumber, '%')) OR " +
            "m.status = :status OR " +
            "m.product.name LIKE CONCAT('%', :product, '%')")
    Page<Subscription> searchByKeyword(Pageable pageable, String reference, String firstName, String lastName, String identificationNumber, SubscriptionStatus status, String product);

    List<Subscription> findByMemberId(Long memberId);
}
