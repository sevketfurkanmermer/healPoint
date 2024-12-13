package com.proje.healpoint.controller;

import com.proje.healpoint.dto.DtoAppointment;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IAppointmentController {
    public ResponseEntity<DtoAppointment> createAppointment(DtoAppointment dtoAppointment);
    public ResponseEntity<List<DtoAppointment>> getUpcomingAppointments();
}
