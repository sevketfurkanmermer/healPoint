package com.proje.healpoint.controller.impl;

import com.proje.healpoint.controller.IAppointmentController;
import com.proje.healpoint.dto.DtoAppointment;
import com.proje.healpoint.dto.DtoDoctorAvailability;
import com.proje.healpoint.model.Appointments;
import com.proje.healpoint.model.Doctors;
import com.proje.healpoint.service.IAppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.LocalDate;
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
}