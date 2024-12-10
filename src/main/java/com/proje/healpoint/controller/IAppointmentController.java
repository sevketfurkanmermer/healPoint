package com.proje.healpoint.controller;

import com.proje.healpoint.dto.DtoAppointment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

public interface IAppointmentController {
    public ResponseEntity<DtoAppointment> createAppointment(DtoAppointment dtoAppointment);
}
