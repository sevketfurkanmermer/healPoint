package com.proje.healpoint.controller.impl;

import com.proje.healpoint.controller.IDoctorController;
import com.proje.healpoint.dto.DtoDoctor;
import com.proje.healpoint.dto.DtoDoctorIU;
import com.proje.healpoint.service.IDoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="/api/v1/doctors")
public class DoctorControllerImpl implements IDoctorController {

    @Autowired
    private IDoctorService doctorService;

    @Override
    @GetMapping(path="/list")
    public List<DtoDoctor> getAllDoctors() {
        return doctorService.getAllDoctors();
    }

    @Override
    @GetMapping(path="/list-token")
    public ResponseEntity<DtoDoctor> getDoctorDetails() {
        DtoDoctor dtoDoctor = doctorService.getDoctorByToken();
        return ResponseEntity.ok(dtoDoctor);
    }

    @Override
    @GetMapping(path = "/list/{id}")
    public ResponseEntity<DtoDoctor> getDoctorByID(@PathVariable("id") String id) {
        DtoDoctor dtoDoctor = doctorService.getDoctorById(id);
        return ResponseEntity.ok(dtoDoctor);
    }
    

    @Override
    @DeleteMapping("/delete")
    public void deleteDoctorById() {
        doctorService.deleteDoctorById();
    }

    @Override
    @PostMapping("/save")
    public DtoDoctor saveDoctor(@RequestBody DtoDoctorIU dtoDoctorIU) {
        return doctorService.saveDoctor(dtoDoctorIU);
    }
    @Override
    @PutMapping(path="/update")
    public DtoDoctor updateDoctorById(@RequestBody DtoDoctorIU dtoDoctorIU) {
        return doctorService.updateDoctorById(dtoDoctorIU);
    }

    @GetMapping("/name")
    @Override
    public ResponseEntity<String> getDoctorName() {
        String doctorName = doctorService.getDoctorNameFromToken();
        return ResponseEntity.ok(doctorName);
    }
}
