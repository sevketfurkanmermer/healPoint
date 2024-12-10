package com.proje.healpoint.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.Period;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Patients")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Patients extends User {
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(nullable = false)
    private Date birthDate;


    @ManyToMany(mappedBy = "patients")
    private List<Doctors> doctors;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Appointments> appointments;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reviews> reviews;


}
