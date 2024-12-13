package com.proje.healpoint.dto;

import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DtoDoctorAvailabilityIU {
    private LocalTime workingHoursStart;
    private LocalTime workingHoursEnd;
}
