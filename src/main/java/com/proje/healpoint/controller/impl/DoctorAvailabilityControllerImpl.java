package com.proje.healpoint.controller.impl;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.proje.healpoint.controller.IDoctorAvailabailityController;
import com.proje.healpoint.dto.DtoDoctorAvailability;
import com.proje.healpoint.dto.DtoDoctorAvailabilityIU;
import com.proje.healpoint.service.IDoctorAvailabilityService;

@RestController
@RequestMapping(path="/api/v1/doctor/availability")
public class DoctorAvailabilityControllerImpl implements IDoctorAvailabailityController {

    @Autowired
    IDoctorAvailabilityService doctorAvailabilityService;

    @Override
    @GetMapping("/times")
    public ResponseEntity<DtoDoctorAvailability> getDoctorAvailability(@RequestParam Date date) {
        DtoDoctorAvailability availabilities = doctorAvailabilityService.getDoctorAvailability(date);
        return ResponseEntity.ok(availabilities);
    }

    @Override
    @PostMapping("/save")
    public DtoDoctorAvailability saveDoctorAvailability(@RequestBody DtoDoctorAvailabilityIU dtoDoctorAvailabilityIU) {
        return doctorAvailabilityService.saveDoctorAvailability(dtoDoctorAvailabilityIU);
    }

    @Override
    @PutMapping("/update/{id}")
    public DtoDoctorAvailability updateDoctorAvailability(@RequestBody DtoDoctorAvailabilityIU dtoDoctorAvailabilityIU, @PathVariable(name = "id") Long id) {
        return doctorAvailabilityService.updateDoctorWorkTimes(id, dtoDoctorAvailabilityIU);
    }

}
