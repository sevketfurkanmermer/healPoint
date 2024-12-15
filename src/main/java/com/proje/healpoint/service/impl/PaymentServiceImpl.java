package com.proje.healpoint.service.impl;

import com.proje.healpoint.dto.DtoPayment;
import com.proje.healpoint.exception.BaseException;
import com.proje.healpoint.exception.ErrorMessage;
import com.proje.healpoint.exception.MessageType;
import com.proje.healpoint.model.Doctors;
import com.proje.healpoint.model.Payments;
import com.proje.healpoint.repository.DoctorRepository;
import com.proje.healpoint.repository.PaymentRepository;
import com.proje.healpoint.service.IPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDateTime;


@Service
public class PaymentServiceImpl implements IPaymentService {
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private SubscriptionServiceImpl subscriptionService;
    @Autowired
    private DoctorRepository doctorRepository;

    public void processPayment(DtoPayment paymentRequest) {
        String doctorTc = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println(paymentRequest.getCvv());
        System.out.println(paymentRequest.getCardNumber());
        System.out.println(paymentRequest.getCardHolderName());
        if (paymentRequest.getCardNumber() == null || paymentRequest.getCardNumber().isEmpty()
                ||paymentRequest.getCvv() == null || paymentRequest.getCvv().isEmpty()
                ||paymentRequest.getCardHolderName() == null || paymentRequest.getCardHolderName().isEmpty()
                ||paymentRequest.getExpiryDate() == null || paymentRequest.getExpiryDate().isEmpty()){
            throw new BaseException(new ErrorMessage(MessageType.INVALID_INPUT,null));
        }
        Doctors doctor = doctorRepository.findById(doctorTc)
                .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, "Doktor bulunamadı")));

        Payments payment = new Payments();
        payment.setAmount(paymentRequest.getAmount());
        payment.setCardNumber(paymentRequest.getCardNumber());
        payment.setCardHolderName(paymentRequest.getCardHolderName());
        payment.setCvv(paymentRequest.getCvv());
        payment.setIsSuccess(true);
        payment.setDoctor(doctor);
        payment.setCreatedTime(LocalDateTime.now());
        paymentRepository.save(payment);
    }
}
