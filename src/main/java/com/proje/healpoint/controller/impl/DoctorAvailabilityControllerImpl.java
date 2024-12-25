package com.proje.healpoint.controller.impl;

import java.sql.Date;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.proje.healpoint.controller.IDoctorAvailabailityController;
import com.proje.healpoint.dto.DtoDoctorAvailability;
import com.proje.healpoint.service.IDoctorAvailabilityService;

@RestController
@RequestMapping(path = "/api/v1/doctor/availability")
public class DoctorAvailabilityControllerImpl implements IDoctorAvailabailityController {

    @Autowired
    IDoctorAvailabilityService doctorAvailabilityService;

    @Override
    @GetMapping("/times/{id}")
    public ResponseEntity<DtoDoctorAvailability> getDoctorAvailability(@PathVariable(name = "id") String doctorTc,
            @RequestParam LocalDate date) {
        DtoDoctorAvailability availabilities = doctorAvailabilityService.getDoctorAvailability(doctorTc, date);
        return ResponseEntity.ok(availabilities);
    }

}
