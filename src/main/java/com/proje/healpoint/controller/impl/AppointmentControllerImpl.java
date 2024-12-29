package com.proje.healpoint.controller.impl;

import com.proje.healpoint.controller.IAppointmentController;
import com.proje.healpoint.dto.DtoAppointment;
import com.proje.healpoint.service.IAppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/appointments")
public class AppointmentControllerImpl implements IAppointmentController {

    @Autowired
    private IAppointmentService appointmentService;

    @PostMapping(path = "/create")
    @Override
    public ResponseEntity<DtoAppointment> createAppointment(@RequestBody DtoAppointment dtoAppointment) {
        DtoAppointment response = appointmentService.createAppointment(dtoAppointment);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/upcoming-appointments")
    public ResponseEntity<List<DtoAppointment>> getUpcomingAppointments() {

        List<DtoAppointment> appointments = appointmentService.getUpcomingAppointments();
        return ResponseEntity.ok(appointments);
    }

    @GetMapping("/completed-and-cancelled")
    public ResponseEntity<List<DtoAppointment>> getCompletedAndCancelledAppointments() {
        List<DtoAppointment> appointments = appointmentService.getCompletedAndCancelledAppointments();
        return ResponseEntity.ok(appointments);

    }
    @GetMapping("/list-patient")
    @Override
    public ResponseEntity<List<DtoAppointment>> getAllAppointmentsByPatient() {
        List<DtoAppointment> appointments = appointmentService.getAllAppointmentsByPatient();
        return ResponseEntity.ok(appointments);
    }
    @GetMapping("/list-doctor")
    @Override
    public ResponseEntity<List<DtoAppointment>> getAllAppointmentsByDoctor() {
        List<DtoAppointment> appointments = appointmentService.getAllAppointmentsByDoctor();
        return ResponseEntity.ok(appointments);
    }
    @GetMapping("/list/patient/active-appointments")
    @Override
    public ResponseEntity<List<DtoAppointment>> getActiveAppointments() {
        List<DtoAppointment> activeAppointments = appointmentService.getActiveAppointments();
        return ResponseEntity.ok(activeAppointments);
    }
    @GetMapping("list/doctor/active-appointments")
    @Override
    public ResponseEntity<List<DtoAppointment>> getDoctorActiveAppointments() {
        List<DtoAppointment> doctorAppointments = appointmentService.getDoctorActiveAppointments();
        return ResponseEntity.ok(doctorAppointments);
    }
    @GetMapping("list/doctor/completed-cancelled")
    @Override
    public ResponseEntity<List<DtoAppointment>> getDoctorCompletedAndCancelledAppointments() {
        List<DtoAppointment> appointments = appointmentService.getDoctorCompletedAndCancelledAppointments();
        return ResponseEntity.ok(appointments);
    }
}