package com.proje.healpoint.service.impl;

import com.proje.healpoint.model.Subscription;
import com.proje.healpoint.repository.DoctorRepository;
import com.proje.healpoint.repository.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class SubscriptionScheduler {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Scheduled(cron = "0 0 0 * * ?") // Her gece 00:00'da çalışır
    public void checkExpiredSubscriptions() {
        List<Subscription> expiredSubscriptions = subscriptionRepository.findByEndDateBeforeAndIsActive(LocalDate.now(), true);

        for (Subscription subscription : expiredSubscriptions) {
            subscription.setIsActive(false);
            subscription.getDoctor().setIsAccountActive(false);
            doctorRepository.save(subscription.getDoctor());
            subscriptionRepository.save(subscription);
        }
    }
}
