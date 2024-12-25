package com.proje.healpoint.controller;

import com.proje.healpoint.dto.DtoDoctor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface IDoctorFilterController {
    public ResponseEntity<List<DtoDoctor>> filterDoctors(
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String district,
            @RequestParam(required = false) String branch,
            @RequestParam(required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate appointmentDate,
            @RequestParam(required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime appointmentTime);
}
