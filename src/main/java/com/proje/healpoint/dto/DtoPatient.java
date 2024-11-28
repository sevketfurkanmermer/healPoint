package com.proje.healpoint.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DtoPatient {
    private Long Patient_id;
    private String Patient_tc;
    private String Patient_name;
    private String Patient_surname;
    private String Patient_gender;
    private String Patient_phonenumber;
    private String Patient_email;
}
