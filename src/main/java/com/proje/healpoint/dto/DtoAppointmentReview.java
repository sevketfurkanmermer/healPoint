package com.proje.healpoint.dto;

import com.proje.healpoint.enums.AppointmentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DtoAppointmentReview {
    private Long Appointment_id;
    private Date Appointment_date;
    private String appointment_time;
    private AppointmentStatus Appointment_status;
    private String Appointment_text;

    private DtoDoctorReview dtoDoctorReview;
    private DtoPatientReview dtoPatientReview;
}
