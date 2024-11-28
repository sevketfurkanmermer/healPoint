package com.proje.healpoint.controller.impl;


import com.proje.healpoint.controller.IPatientController;
import com.proje.healpoint.dto.DtoPatient;
import com.proje.healpoint.dto.DtoPatientIU;
import com.proje.healpoint.service.IPatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/api/patients")
public class PatientControllerImpl implements IPatientController {
    @Autowired
    private IPatientService patientService;

    @PostMapping(path="/create")
    @Override
    public ResponseEntity<String> createPatient(@RequestBody DtoPatientIU dtoPatientIU) {
        String response = patientService.createPatient(dtoPatientIU);
        return ResponseEntity.ok(response);
    }
}
