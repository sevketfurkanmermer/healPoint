package com.proje.healpoint.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "Reviews", uniqueConstraints = {
        @UniqueConstraint(columnNames = "appointment_id")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reviews {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Review_id;
    private String comments;
    @Max(5)
    @Min(1)
    private int points;
    @CreationTimestamp
    private LocalDateTime created_at;
    @ManyToOne
    @JoinColumn(name = "patient_tc", nullable = false)
    private Patients patient;

    @ManyToOne
    @JoinColumn(name = "doctor_tc", nullable = false)
    private Doctors doctor;

    @OneToOne
    @JoinColumn(name = "appointment_id", nullable = false)
    private Appointments appointment;
}
