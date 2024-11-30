package com.proje.healpoint.controller;

import com.proje.healpoint.dto.DtoAppointment;
import org.springframework.http.ResponseEntity;

public interface IAppointmentController {
    public ResponseEntity<String> createAppointment(DtoAppointment dtoAppointment);
}
