package com.proje.healpoint.controller;

import java.sql.Date;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.proje.healpoint.dto.DtoDoctorAvailability;
import com.proje.healpoint.dto.DtoDoctorAvailabilityIU;

public interface IDoctorAvailabailityController {
    public ResponseEntity<DtoDoctorAvailability> getDoctorAvailability(@PathVariable(name = "id") String doctorTc,
            @RequestParam Date date);
    public DtoDoctorAvailability saveDoctorAvailability(@RequestBody DtoDoctorAvailabilityIU dtoDoctorAvailabilityIU);
    public DtoDoctorAvailability saveDoctorAvailability(@RequestBody DtoDoctorAvailabilityIU dtoDoctorAvailabilityIU, @PathVariable(name = "id") Long id);
}
