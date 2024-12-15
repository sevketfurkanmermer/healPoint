package com.proje.healpoint.controller.impl;

import com.proje.healpoint.controller.IAdminController;
import com.proje.healpoint.dto.DtoSubscriptionPlan;
import com.proje.healpoint.exception.BaseException;
import com.proje.healpoint.service.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/admin/plans")
public class AdminControllerImpl implements IAdminController {
    @Autowired
    private IAdminService adminService;
    @PostMapping(path = "/add")
    public ResponseEntity<DtoSubscriptionPlan> addPlan(@RequestBody DtoSubscriptionPlan dtoPlan) {
            DtoSubscriptionPlan createdPlan = adminService.addPlan(dtoPlan);
            return new ResponseEntity<>(createdPlan, HttpStatus.CREATED);
    }
    @PutMapping("/update/{planId}")
    public ResponseEntity<DtoSubscriptionPlan> updatePlan(@PathVariable Long planId, @RequestBody DtoSubscriptionPlan dtoUpdatedPlan) {

        DtoSubscriptionPlan updatedPlan = adminService.updatePlan(planId, dtoUpdatedPlan);
        return new ResponseEntity<>(updatedPlan, HttpStatus.OK);
    }
    @GetMapping(path = "/list")
    public ResponseEntity<List<DtoSubscriptionPlan>> getAllPlans() {
        List<DtoSubscriptionPlan> plans = adminService.getAllPlans();
        return new ResponseEntity<>(plans, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{planId}")
    public ResponseEntity<Void> deletePlan(@PathVariable Long planId) {
            adminService.deletePlan(planId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
