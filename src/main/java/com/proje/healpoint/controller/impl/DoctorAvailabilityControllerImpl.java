package com.proje.healpoint.controller.impl;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.proje.healpoint.controller.IDoctorAvailabailityController;
import com.proje.healpoint.dto.DtoDoctorAvailability;
import com.proje.healpoint.dto.DtoDoctorAvailabilityIU;
import com.proje.healpoint.service.IDoctorAvailabilityService;

@RestController
@RequestMapping(path="/api/doctor/availability")
public class DoctorAvailabilityControllerImpl implements IDoctorAvailabailityController {

    @Autowired
    IDoctorAvailabilityService doctorAvailabilityService;

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<DtoDoctorAvailability> getDoctorAvailability(@PathVariable(name = "id") String doctorTc,
            @RequestParam Date date) {
        DtoDoctorAvailability availabilities = doctorAvailabilityService.getDoctorAvailability(doctorTc, date);
        return ResponseEntity.ok(availabilities);
    }

    @Override
    @PostMapping("/save")
    public DtoDoctorAvailability saveDoctorAvailability(@RequestBody DtoDoctorAvailabilityIU dtoDoctorAvailabilityIU) {
        return doctorAvailabilityService.saveDoctorAvailability(dtoDoctorAvailabilityIU);
    }

    @Override
    @PostMapping("/update/{id}")
    public DtoDoctorAvailability saveDoctorAvailability(@RequestBody DtoDoctorAvailabilityIU dtoDoctorAvailabilityIU, @PathVariable(name = "id") Long id) {
        return doctorAvailabilityService.updateDoctorWorkTimes(id, dtoDoctorAvailabilityIU);
    }

}
