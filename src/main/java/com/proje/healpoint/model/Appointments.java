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
@Table(name = "Appointments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Appointments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Appointment_id;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date Appointment_date;

    @Enumerated(EnumType.STRING)
    private AppointmentStatus Appointment_status;

    private String Appointment_text;

    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;










}
