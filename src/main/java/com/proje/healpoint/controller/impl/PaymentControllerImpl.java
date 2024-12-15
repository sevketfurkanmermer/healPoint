package com.proje.healpoint.controller.impl;

import com.proje.healpoint.controller.IPaymentController;
import com.proje.healpoint.dto.DtoPayment;
import com.proje.healpoint.service.impl.PaymentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/payment")
public class PaymentControllerImpl implements IPaymentController {
    @Autowired
    private PaymentServiceImpl paymentService;

    @Override
    @PostMapping("/process")
    public ResponseEntity<String> processPayment(@RequestBody DtoPayment paymentRequest) {

        paymentService.processPayment(paymentRequest);
        return ResponseEntity.ok("Ödeme başarıyla gerçekleştirildi ve abonelik başlatıldı.");

    }
}
