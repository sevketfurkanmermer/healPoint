package com.proje.healpoint.repository;

import com.proje.healpoint.model.Doctors;
import com.proje.healpoint.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    List<Subscription> findByEndDateBeforeAndIsActive(LocalDate endDate, Boolean isActive);
    Optional<Subscription> findByDoctor(Doctors doctor);
}
