package com.proje.healpoint.controller.impl;

import com.proje.healpoint.controller.IDoctorFilterController;
import com.proje.healpoint.dto.DtoDoctor;
import com.proje.healpoint.service.IDoctorFilterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/doctors")
public class DoctorFilterControllerImpl implements IDoctorFilterController {
    @Autowired
    private IDoctorFilterService doctorFilterService;

    @GetMapping("/filter")
    @Override
    public ResponseEntity<List<DtoDoctor>> filterDoctors(
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String district,
            @RequestParam(required = false) String branch,
            @RequestParam(required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate appointmentDate,
            @RequestParam(required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime appointmentTime) {

        List<DtoDoctor> doctors = doctorFilterService.filterDoctors(city, district, branch, appointmentDate, appointmentTime);
        return ResponseEntity.ok(doctors);
    }
}
