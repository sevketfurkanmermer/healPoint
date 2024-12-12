package com.proje.healpoint.controller.impl;

import java.sql.Date;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.proje.healpoint.controller.IDoctorAvailabailityController;
import com.proje.healpoint.dto.DtoDoctorAvailability;
import com.proje.healpoint.service.IDoctorAvailabilityService;

@RestController
public class DoctorAvailabilityControllerImpl implements IDoctorAvailabailityController {

    @Autowired
    IDoctorAvailabilityService doctorAvailabilityService;

    @GetMapping("/availability/{id}")
    @Override
    public ResponseEntity<DtoDoctorAvailability> getDoctorAvailability(@PathVariable(name = "id") String doctorTc,
            @RequestParam Date date) {
        // LocalDate localDate = LocalDate.parse(date);
        DtoDoctorAvailability availabilities = doctorAvailabilityService.getDoctorAvailability(doctorTc, date);
        return ResponseEntity.ok(availabilities);
    }

}
