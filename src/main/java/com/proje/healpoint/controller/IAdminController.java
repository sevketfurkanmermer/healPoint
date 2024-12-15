package com.proje.healpoint.controller;

import com.proje.healpoint.dto.DtoSubscriptionPlan;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface IAdminController {
    public ResponseEntity<DtoSubscriptionPlan> addPlan(DtoSubscriptionPlan dtoPlan);

    public ResponseEntity<DtoSubscriptionPlan> updatePlan(Long planId,  DtoSubscriptionPlan dtoUpdatedPlan);

    public ResponseEntity<List<DtoSubscriptionPlan>> getAllPlans();

    public ResponseEntity<Void> deletePlan(Long planId);
}
