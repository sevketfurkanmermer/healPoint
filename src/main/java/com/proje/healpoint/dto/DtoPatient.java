package com.proje.healpoint.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DtoPatient {

    private String Patient_tc;
    private String Patient_name;
    private String Patient_surname;
    private String Patient_gender;
    private String Patient_phonenumber;
    private String Patient_email;

    private List<DtoReview> reviews;
}
