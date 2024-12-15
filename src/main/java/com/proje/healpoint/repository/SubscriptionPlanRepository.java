package com.proje.healpoint.repository;

import com.proje.healpoint.model.SubscriptionPlan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionPlanRepository extends JpaRepository<SubscriptionPlan, Long> {
    boolean existsByName(String name);

    boolean existsByNameAndDurationInMonths(String name, Integer durationInMonths);

    SubscriptionPlan findByNameAndDurationInMonths(String name, Integer durationInMonths);
}
