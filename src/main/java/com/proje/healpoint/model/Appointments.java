package com.proje.healpoint.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.proje.healpoint.enums.AppointmentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

@Entity
@Table(name = "Appointments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Appointments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long appointmentId;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate appointmentDate;

    @Column(nullable = false)
    private LocalTime appointmentTime;

    @Enumerated(EnumType.STRING)
    private AppointmentStatus appointmentStatus;
    @Column(nullable = false)
    private boolean reminderSent = false;

    private String appointmentText;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "patient_tc", nullable = false)
    private Patients patient;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "doctor_tc", nullable = false)
    private Doctors doctor;
    @OneToOne(mappedBy = "appointment", cascade = CascadeType.ALL, orphanRemoval = true)
    private Reviews review;
}
