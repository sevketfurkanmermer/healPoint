package com.proje.healpoint.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.proje.healpoint.enums.AppointmentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DtoAppointmentReview {
    private Long appointmentId;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate Appointment_date;
    
    private LocalTime appointment_time;
    private AppointmentStatus Appointment_status;
    private String Appointment_text;

    private DtoDoctorReview dtoDoctorReview;
    private DtoPatientReview dtoPatientReview;
}
