package com.proje.healpoint.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.proje.healpoint.enums.AppointmentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "Appointments", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"doctor_id", "appointment_date", "appointment_time"})
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Appointments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long appointmentId;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date appointmentDate;

    @Column(nullable = false)
    private String appointmentTime;

    @Enumerated(EnumType.STRING)
    private AppointmentStatus appointmentStatus;

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
