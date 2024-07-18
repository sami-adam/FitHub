package com.fithub.repository.subscription;

import com.fithub.model.subscription.Subscription;
import com.fithub.model.subscription.SubscriptionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    @Query("select m from Subscription as m where m.reference like %:reference% or m.member.firstName like %:firstName% or m.member.lastName like %:lastName% or m.member.identificationNumber like %:identificationNumber% or m.status = :status")
    List<Subscription> searchByKeyword(String reference, String firstName, String lastName, String identificationNumber, SubscriptionStatus status);
}
