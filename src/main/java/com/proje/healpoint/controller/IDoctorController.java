package com.proje.healpoint.controller;

import com.proje.healpoint.dto.DtoDoctor;
import com.proje.healpoint.dto.DtoDoctorIU;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IDoctorController {

    public List<DtoDoctor> getAllDoctors();
    public ResponseEntity<DtoDoctor> getDoctorDetails();
    public void deleteDoctorById();
    public DtoDoctor saveDoctor(DtoDoctorIU dtoDoctorIU);
    public DtoDoctor updateDoctorById(DtoDoctorIU dtoDoctorIU);
    public ResponseEntity<String> getDoctorName();
}
