package com.proje.healpoint.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name ="Payments" )
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long PaymentId;

    @ManyToOne
    @JoinColumn(name = "doctor_tc", nullable = false)
    private Doctors doctor;
    @Column(nullable = false,length = 16)
    private String cardNumber;
    @Column(nullable = false)
    private String cardHolderName;
    @Column(nullable = false)
    private BigDecimal amount;
    @Column(nullable = false ,length = 3)
    private String cvv;

    private Boolean isSuccess;

    private LocalDateTime createdTime;
}
