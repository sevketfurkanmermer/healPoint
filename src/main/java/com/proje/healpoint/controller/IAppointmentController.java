package com.proje.healpoint.controller;

import com.proje.healpoint.dto.DtoAppointment;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IAppointmentController {
    public ResponseEntity<DtoAppointment> createAppointment(DtoAppointment dtoAppointment);
    public ResponseEntity<List<DtoAppointment>> getUpcomingAppointments();
    public ResponseEntity<List<DtoAppointment>> getCompletedAndCancelledAppointments();
    public ResponseEntity<List<DtoAppointment>> getAllAppointmentsByPatient();
    public ResponseEntity<List<DtoAppointment>> getAllAppointmentsByDoctor();
    public ResponseEntity<List<DtoAppointment>> getActiveAppointments();
    public ResponseEntity<List<DtoAppointment>> getDoctorActiveAppointments();
    public ResponseEntity<List<DtoAppointment>> getDoctorCompletedAndCancelledAppointments();
    public ResponseEntity<DtoAppointment> cancelAppointment(Long id);
}
