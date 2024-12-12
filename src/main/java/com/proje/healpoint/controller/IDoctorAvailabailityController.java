package com.proje.healpoint.controller;

import java.sql.Date;
import java.time.LocalDate;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.proje.healpoint.dto.DtoDoctorAvailability;

public interface IDoctorAvailabailityController {
    public ResponseEntity<DtoDoctorAvailability> getDoctorAvailability(@PathVariable(name = "id") String doctorTc,
            @RequestParam Date date);
}
