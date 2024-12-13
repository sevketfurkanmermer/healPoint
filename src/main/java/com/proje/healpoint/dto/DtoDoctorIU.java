package com.proje.healpoint.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DtoDoctorIU extends DtoUserIU {
    private String branch;
    private String about;
    private String city;
    private String district;
    private String address;
    private LocalTime workingHoursStart;
    private LocalTime workingHoursEnd;
}
