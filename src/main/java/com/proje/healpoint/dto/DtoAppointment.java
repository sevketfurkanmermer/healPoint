package com.proje.healpoint.dto;

import com.proje.healpoint.enums.AppointmentStatus;
import com.proje.healpoint.model.Reviews;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DtoAppointment {
    private Long appointmentId;
    private Date appointmentDate;
    private String appointmentTime;
    private AppointmentStatus appointmentStatus;
    private String appointmentText;

    private String patientTc;
    private String doctorTc;

    private DtoDoctorReview doctor;
    private List<DtoReview> reviews;

}
