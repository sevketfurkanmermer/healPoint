package com.proje.healpoint.dto;

import com.proje.healpoint.model.Patients;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DtoReview {
    private Long Review_id;
    private String comments;
    private int points;
    private DtoAppointmentReview appointment;
    private LocalDateTime created_at;
    private DtoPatientReview patient;
}
