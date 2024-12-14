package com.proje.healpoint.service.impl;

import com.proje.healpoint.dto.DtoSubscription;
import com.proje.healpoint.exception.BaseException;
import com.proje.healpoint.exception.ErrorMessage;
import com.proje.healpoint.exception.MessageType;
import com.proje.healpoint.model.Doctors;
import com.proje.healpoint.model.Subscription;
import com.proje.healpoint.model.SubscriptionPlan;
import com.proje.healpoint.repository.DoctorRepository;
import com.proje.healpoint.repository.SubscriptionPlanRepository;
import com.proje.healpoint.repository.SubscriptionRepository;
import com.proje.healpoint.service.ISubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.Optional;

@Service
public class SubscriptionServiceImpl implements ISubscriptionService {
    @Autowired
    private SubscriptionRepository subscriptionRepository;
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private SubscriptionPlanRepository subscriptionPlanRepository;

    @Override
//    public DtoSubscription startSubscription(DtoSubscription dtoSubscription) {
//
//        String doctorTc = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//
//        Optional<Doctors> optionalDoctor = doctorRepository.findById(doctorTc);
//        if (optionalDoctor.isEmpty()) {
//            throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, "Doktor bulunamadı"));
//        }
//        Doctors doctor = optionalDoctor.get();
//
//        Optional<SubscriptionPlan> plan = subscriptionPlanRepository.findById(dtoSubscription.getPlanId());
//        if (plan.isEmpty()) {
//            throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, "Abonelik planı bulunamadı"));
//        }
//        Subscription subscription = new Subscription();
//        subscription.setDoctor(doctor);
//        subscription.setPlan(plan.get());
//        subscription.setStartDate(LocalDate.now());
//        subscription.setEndDate(LocalDate.now().plusMonths(plan.get().getDurationInMonths()));
//        subscription.setIsActive(true);
//
//        doctor.setIsAccountActive(true);
//        doctorRepository.save(doctor);
//
//        Subscription savedSubscription = subscriptionRepository.save(subscription);
//
//        DtoSubscription response = new DtoSubscription();
//        response.setPlanId(savedSubscription.getPlan().getId());
//        response.setPlanName(savedSubscription.getPlan().getName());
//        response.setStartDate(savedSubscription.getStartDate());
//        response.setEndDate(savedSubscription.getEndDate());
//        response.setIsActive(savedSubscription.getIsActive());
//        return response;
//    }

    public DtoSubscription manageSubscription(Long planId) {
        String doctorTc = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Doctors doctor = doctorRepository.findById(doctorTc)
                .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, "Doktor bulunamadı")));

        SubscriptionPlan plan = subscriptionPlanRepository.findById(planId)
                .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, "Abonelik planı bulunamadı")));

        Subscription subscription = doctor.getSubscription();

        if (subscription == null) {
            subscription = new Subscription();
            subscription.setDoctor(doctor);
            subscription.setPlan(plan);
            subscription.setStartDate(LocalDate.now());
            subscription.setEndDate(LocalDate.now().plusMonths(plan.getDurationInMonths()));
            subscription.setIsActive(true);
        } else {
            if (subscription.getEndDate().isBefore(LocalDate.now())) {
                // Abonelik süresi geçmişse bugünün tarihinden itibaren başlat
                subscription.setStartDate(LocalDate.now());
                subscription.setEndDate(LocalDate.now().plusMonths(plan.getDurationInMonths()));
            } else {
                // Abonelik aktifse mevcut bitiş tarihine süre ekle
                subscription.setEndDate(subscription.getEndDate().plusMonths(plan.getDurationInMonths()));
            }
            subscription.setIsActive(true);
        }

        doctor.setIsAccountActive(true);
        doctor.setSubscription(subscription);

        doctorRepository.save(doctor);

        DtoSubscription response = new DtoSubscription();
        response.setPlanId(subscription.getPlan().getId());
        response.setPlanName(subscription.getPlan().getName());
        response.setStartDate(subscription.getStartDate());
        response.setEndDate(subscription.getEndDate());
        response.setIsActive(subscription.getIsActive());

        return response;
    }
}