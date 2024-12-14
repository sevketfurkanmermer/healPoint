package com.proje.healpoint.controller;

import com.proje.healpoint.dto.DtoSubscription;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

public interface ISubscriptionController {
    //public ResponseEntity<DtoSubscription> startSubscription(DtoSubscription dtoSubscription);
    public ResponseEntity<DtoSubscription> manageSubscription(Long planId);
}
