package com.proje.healpoint.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DtoPatientIU {

    private String patientTc;
    private String Patient_name;
    private String Patient_surname;
    private String Patient_gender;
    private String patientPhonenumber;
    private String patientEmail;
    private String Patient_password;

}
