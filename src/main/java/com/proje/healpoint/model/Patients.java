package com.proje.healpoint.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "Patients")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Patients {

    @Id
    @Column(nullable = false,length = 11)
    private String patientTc;
    @Column(nullable = false)
    private String Patient_name;
    @Column(nullable = false)
    private String Patient_surname;

    private String Patient_gender;
    @Column(nullable = false,unique = true)
    private String patientPhonenumber;
    @Column(nullable = false,unique = true)
    private String patientEmail;
    @Column(nullable = false)
    private String Patient_password;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToMany(mappedBy = "patients")
    private List<Doctors> doctors;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Appointments> appointments;

    //@OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    //private List<Reviews> reviews ;


}
