package com.proje.healpoint.controller.impl;

import com.proje.healpoint.controller.IAppointmentController;
import com.proje.healpoint.dto.DtoAppointment;
import com.proje.healpoint.service.IAppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/appointments")
public class AppointmentControllerImpl implements IAppointmentController {

    @Autowired
    private IAppointmentService appointmentService;

    @PostMapping(path = "/create")
    @Override
    public ResponseEntity<String> createAppointment(@RequestBody DtoAppointment dtoAppointment) {
        String response = appointmentService.createAppointment(dtoAppointment);
        return ResponseEntity.ok(response);
    }
}
