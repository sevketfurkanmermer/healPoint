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
    private Long Appointment_id;
    private Date Appointment_date;
    private String appointment_time;
    private AppointmentStatus Appointment_status;
    private String Appointment_text;

    private String patientTc;
    private String doctorTc;

    private DtoDoctorReview doctor;
    private List<DtoReview> reviews;

}
