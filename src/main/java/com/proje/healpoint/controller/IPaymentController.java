package com.proje.healpoint.controller;

import com.proje.healpoint.dto.DtoPayment;
import org.springframework.http.ResponseEntity;

public interface IPaymentController {
    public ResponseEntity<String> processPayment(DtoPayment paymentRequest);
}
