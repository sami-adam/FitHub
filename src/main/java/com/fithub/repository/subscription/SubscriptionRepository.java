package com.fithub.repository.subscription;

import com.fithub.model.subscription.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    @Query("select m from Subscription as m where m.member.firstName like %:firstName% or m.member.lastName like %:lastName%")
    List<Subscription> searchByKeyword(String firstName, String lastName);
}
