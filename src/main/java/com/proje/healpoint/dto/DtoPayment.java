package com.proje.healpoint.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DtoPayment {
    private BigDecimal amount;
    private String cardNumber;
    private String cardHolderName;
    private String expiryDate;
    private String cvv;
    private Long planId;
}
