package com.proje.healpoint.controller.impl;

import com.proje.healpoint.controller.ISubscriptionController;
import com.proje.healpoint.dto.DtoSubscription;
import com.proje.healpoint.service.ISubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/subscription")
public class SubscriptionControllerImpl implements ISubscriptionController {
    @Autowired
    private ISubscriptionService subscriptionService;

//    @PostMapping(path="/start")
//    @Override
//    public ResponseEntity<DtoSubscription> startSubscription(DtoSubscription dtoSubscription) {
//        DtoSubscription response = subscriptionService.startSubscription(dtoSubscription);
//        return ResponseEntity.ok(response);
//    }

    @PostMapping("/manage")
    public ResponseEntity<DtoSubscription> manageSubscription(@RequestParam Long planId) {
        DtoSubscription subscription = subscriptionService.manageSubscription(planId);
        return ResponseEntity.ok(subscription);
    }
}
