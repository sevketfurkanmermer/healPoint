package com.proje.healpoint.controller;

import com.proje.healpoint.dto.DtoPatient;
import com.proje.healpoint.dto.DtoPatientIU;
import org.springframework.http.ResponseEntity;

public interface IPatientController {

    public ResponseEntity<String> createPatient(DtoPatientIU dtoPatientIU);

    public ResponseEntity<String> updatePatient(String Patient_tc,DtoPatientIU dtoPatientIU);

    public DtoPatient getPatient(String Patient_tc);
}
