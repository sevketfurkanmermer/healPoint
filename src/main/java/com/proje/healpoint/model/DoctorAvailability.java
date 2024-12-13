package com.proje.healpoint.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@Entity
@Table(name = "doctorAvailabilitiys")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DoctorAvailability {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private LocalTime workingHoursStart;
    private LocalTime workingHoursEnd;
    @OneToOne()
    @JoinColumn(name = "doctor_tc", nullable = false)
    private Doctors doctor;
}
