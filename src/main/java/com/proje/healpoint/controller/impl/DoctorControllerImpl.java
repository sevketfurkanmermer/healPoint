package com.proje.healpoint.controller.impl;

import com.proje.healpoint.controller.IDoctorController;
import com.proje.healpoint.dto.DtoDoctor;
import com.proje.healpoint.dto.DtoDoctorIU;
import com.proje.healpoint.service.IDoctorService;
import org.springframework.beans.factory.annotation.Autowired;
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
    @GetMapping(path="/list/{id}")
    public DtoDoctor getDoctorById(@PathVariable(name = "id") String doctorTc) {
        return doctorService.getDoctorById(doctorTc);
    }

    @Override
    @DeleteMapping("/delete/{id}")
    public void deleteDoctorById(@PathVariable(name = "id") String doctorTc) {
        doctorService.deleteDoctorById(doctorTc);
    }  

    @Override
    @PostMapping("/save")
    public DtoDoctor saveDoctor(@RequestBody DtoDoctorIU dtoDoctorIU) {
        return doctorService.saveDoctor(dtoDoctorIU);
    }

    @Override
    @PutMapping(path="/{id}")
    public DtoDoctor updateDoctorById(@RequestBody DtoDoctorIU dtoDoctorIU, @PathVariable(name = "id") String doctorTc) {
        return doctorService.updateDoctor(doctorTc, dtoDoctorIU);
    }
}
