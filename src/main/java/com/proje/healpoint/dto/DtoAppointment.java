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
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DtoAppointment {
    private Long appointmentId;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate appointmentDate;

    private LocalTime appointmentTime;
    private AppointmentStatus appointmentStatus;
    private String appointmentText;

    private String patientTc;
    private String doctorTc;

    private DtoDoctorReview doctor;
    private List<DtoReview> reviews;
}
