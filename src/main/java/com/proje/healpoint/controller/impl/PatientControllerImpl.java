package com.proje.healpoint.controller.impl;


import com.proje.healpoint.controller.IPatientController;
import com.proje.healpoint.dto.DtoPatient;
import com.proje.healpoint.dto.DtoPatientIU;
import com.proje.healpoint.service.IPatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/api/patients")
public class PatientControllerImpl implements IPatientController {
    @Autowired
    private IPatientService patientService;

    @PostMapping(path="/create")
    @Override
    public ResponseEntity<String> createPatient(@RequestBody DtoPatientIU dtoPatientIU) {
        String response = patientService.createPatient(dtoPatientIU);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping(path="/update/{Patient_tc}")
    @Override
    public ResponseEntity<String> updatePatient(@PathVariable(name = "Patient_tc")String Patient_tc, @RequestBody DtoPatientIU dtoPatientIU) {
        String response = patientService.updatePatient(Patient_tc, dtoPatientIU);
            return ResponseEntity.ok(response);
    }
    @GetMapping(path="/list/{Patient_tc}")
    @Override
    public DtoPatient getPatient(@PathVariable(name = "Patient_tc") String Patient_tc) {
        return patientService.getPatientById(Patient_tc);

    }
}
