package com.proje.healpoint.dto;

import com.proje.healpoint.model.Appointments;
import com.proje.healpoint.model.Doctors;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DtoPatient {

    private String patientTc;
    private String Patient_name;
    private String Patient_surname;
    private String Patient_gender;
    private String patientPhonenumber;
    private String patientEmail;


    private List<DtoAppointment> appointments = new ArrayList<>();


    private List<DtoReview> reviews;
}
