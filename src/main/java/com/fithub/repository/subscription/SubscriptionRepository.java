package com.fithub.repository.subscription;

import com.fithub.model.subscription.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "subscription", path = "subscription")
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
}
