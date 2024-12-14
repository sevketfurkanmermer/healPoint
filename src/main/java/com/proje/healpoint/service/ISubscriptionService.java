package com.proje.healpoint.service;

import com.proje.healpoint.dto.DtoSubscription;
import com.proje.healpoint.model.Subscription;

public interface ISubscriptionService {
    //public DtoSubscription startSubscription(DtoSubscription dtoSubscription);
    public DtoSubscription manageSubscription(Long planId);
}
