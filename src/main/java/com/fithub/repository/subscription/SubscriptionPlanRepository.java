package com.fithub.repository.subscription;

import com.fithub.model.subscription.SubscriptionPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RepositoryRestResource(collectionResourceRel = "subscriptionPlan", path = "subscriptionPlan")
public interface SubscriptionPlanRepository extends JpaRepository<SubscriptionPlan, Long> {
}
