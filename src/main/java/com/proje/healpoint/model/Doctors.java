package com.proje.healpoint.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "Doctors")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Doctors extends User {

    private String branch;
    private String about;
    private String city;
    private String district;
    private String address;
    private Double avgPoint;

    @ManyToMany
    @JoinTable(name="Doctor_patient",
            joinColumns = @JoinColumn(name="doctor_tc"),
            inverseJoinColumns = @JoinColumn(name="patient_tc"))
    private List<Patients> patients;
    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Payments> payments;
    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Appointments> appointments;
    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reviews> reviews;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private DoctorAvailability availability;
}
