package com.proje.healpoint.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.proje.healpoint.model.Doctors;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DtoDoctorAvailability {
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate availableDate;
    private List<LocalTime> availableTimes;
    private LocalTime workingHoursStart;
    private LocalTime workingHoursEnd;

}
