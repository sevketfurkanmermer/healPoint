package com.proje.healpoint.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DtoDoctorReview {
    private String name;
    private String surname;
    private String branch;
    private String city;
    private String email;
}
