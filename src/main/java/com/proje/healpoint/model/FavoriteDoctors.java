package com.proje.healpoint.model;

import jakarta.persistence.*;
import jdk.jfr.Enabled;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name="favorite_doctors")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class FavoriteDoctors {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long FavId;
    @ManyToOne
    @JoinColumn(name = "patient_tc", nullable = false)
    private Patients patient;
    @ManyToOne
    @JoinColumn(name = "doctor_tc", nullable = false)
    private Doctors doctor;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
