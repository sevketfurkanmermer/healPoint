package com.proje.healpoint.model;

import jakarta.persistence.*;
import jdk.jfr.Name;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "Doctors")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Doctors {

    @Id
    @Column(length = 11, nullable = false)
    private String Doctor_tc;
    @Column(nullable = false)
    private String Doctor_name;
    @Column(nullable = false)
    private String Doctor_surname;
    @Column(nullable = false)
    private String Doctor_phonenumber;
    @Column(nullable = false)
    private String Doctor_password;
    @Column(nullable = false,unique = true)
    private String Doctor_email;
    @Column(nullable = false)
    private String city;
    @Column(nullable = false)
    private String district;
    @Column(nullable = false)
    private String address;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToMany
    @JoinTable(name="Doctor_patient",
            joinColumns = @JoinColumn(name="doctor_tc"),
            inverseJoinColumns = @JoinColumn(name="patient_tc"))
    private List<Patients> patients;


    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Payments> payments;

    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Reviews> reviews;



}
