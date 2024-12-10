package com.proje.healpoint.service;

import com.proje.healpoint.dto.DtoPatient;
import com.proje.healpoint.dto.DtoPatientIU;

public interface IPatientService {

    public String createPatient(DtoPatientIU dtoPatientIU);

    public  DtoPatient updatePatient(DtoPatientIU dtoPatientIU);

    public DtoPatient getPatientById(String Patient_tc);

}
