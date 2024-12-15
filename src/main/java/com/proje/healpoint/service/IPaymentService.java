package com.proje.healpoint.service;

import com.proje.healpoint.dto.DtoPayment;

public interface IPaymentService {
    public void processPayment(DtoPayment paymentRequest);
}
