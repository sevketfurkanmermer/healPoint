package com.proje.healpoint.service.impl;

import com.proje.healpoint.dto.DtoSubscriptionPlan;
import com.proje.healpoint.exception.BaseException;
import com.proje.healpoint.exception.ErrorMessage;
import com.proje.healpoint.exception.MessageType;
import com.proje.healpoint.jwt.JwtService;
import com.proje.healpoint.model.SubscriptionPlan;
import com.proje.healpoint.repository.AdminRepository;
import com.proje.healpoint.repository.SubscriptionPlanRepository;
import com.proje.healpoint.service.IAdminService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminServiceImpl implements IAdminService {
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private SubscriptionPlanRepository subscriptionPlanRepository;
    @Autowired
    private JwtService jwtService;

    @Override
    public DtoSubscriptionPlan addPlan(DtoSubscriptionPlan dtoPlan) {
        if (subscriptionPlanRepository.existsByName(dtoPlan.getName())) {
            throw new BaseException(new ErrorMessage(MessageType.DUPLICATE_RECORD, "Plan adı zaten mevcut"));
        }

        if (subscriptionPlanRepository.existsByNameAndDurationInMonths(dtoPlan.getName(), dtoPlan.getDurationInMonths())) {
            throw new BaseException(new ErrorMessage(MessageType.DUPLICATE_RECORD, "Aynı ay için aynı plan mevcut"));
        }

        SubscriptionPlan plan = new SubscriptionPlan();
        BeanUtils.copyProperties(dtoPlan, plan);
        SubscriptionPlan savedPlan = subscriptionPlanRepository.save(plan);

        DtoSubscriptionPlan response = new DtoSubscriptionPlan();
        BeanUtils.copyProperties(savedPlan, response);
        return response;
    }

    public DtoSubscriptionPlan updatePlan(Long planId, DtoSubscriptionPlan dtoUpdatedPlan) {

        SubscriptionPlan existingPlan = subscriptionPlanRepository.findById(planId)
                .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, "Plan bulunamadı")));
        if (subscriptionPlanRepository.existsByNameAndDurationInMonths(dtoUpdatedPlan.getName(), dtoUpdatedPlan.getDurationInMonths())) {
            throw new BaseException(new ErrorMessage(MessageType.DUPLICATE_RECORD, "Aynı ay için aynı plan mevcut"));
        }
        if (!existingPlan.getName().equals(dtoUpdatedPlan.getName()) && subscriptionPlanRepository.existsByName(dtoUpdatedPlan.getName())) {
            throw new BaseException(new ErrorMessage(MessageType.DUPLICATE_RECORD, "Plan adı zaten mevcut"));
        }
        existingPlan.setName(dtoUpdatedPlan.getName());
        existingPlan.setPrice(dtoUpdatedPlan.getPrice());
        existingPlan.setDurationInMonths(dtoUpdatedPlan.getDurationInMonths());

        SubscriptionPlan updatedPlan = subscriptionPlanRepository.save(existingPlan);

        DtoSubscriptionPlan response = new DtoSubscriptionPlan();
        BeanUtils.copyProperties(updatedPlan, response);
        return response;
    }
    @Override
    public List<DtoSubscriptionPlan> getAllPlans() {
        List<SubscriptionPlan> plans = subscriptionPlanRepository.findAll();
        return plans.stream().map(plan -> {
            DtoSubscriptionPlan dtoPlan = new DtoSubscriptionPlan();
            BeanUtils.copyProperties(plan, dtoPlan);
            return dtoPlan;
        }).collect(Collectors.toList());
    }
    @Override
    public void deletePlan(Long planId) {
        if (!subscriptionPlanRepository.existsById(planId)) {
            throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, "Plan bulunamadı"));
        }
        subscriptionPlanRepository.deleteById(planId);
    }
}
