package com.ignite.events_microservice.repositories;

import com.ignite.events_microservice.domain.Subscripbtion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository extends JpaRepository<Subscripbtion, Long> {
}
