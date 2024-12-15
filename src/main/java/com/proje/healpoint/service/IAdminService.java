package com.proje.healpoint.service;

import com.proje.healpoint.dto.DtoSubscriptionPlan;

import java.util.List;


public interface IAdminService {
     public DtoSubscriptionPlan addPlan(DtoSubscriptionPlan dtoPlan);

    public DtoSubscriptionPlan updatePlan(Long planId, DtoSubscriptionPlan dtoUpdatedPlan);

    public List<DtoSubscriptionPlan> getAllPlans();

    public void deletePlan(Long planId);
}
