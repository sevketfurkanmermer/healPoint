package com.proje.healpoint.controller;

import com.proje.healpoint.dto.DtoDoctor;
import com.proje.healpoint.dto.DtoDoctorIU;

import java.util.List;

public interface IDoctorController {

    public List<DtoDoctor> getAllDoctors();
    public DtoDoctor getDoctorById(String doctorTc);
    public void deleteDoctorById(String doctorTc);
    public DtoDoctor saveDoctor(DtoDoctorIU dtoDoctorIU);
    public DtoDoctor updateDoctorById(DtoDoctorIU dtoDoctorIU, String doctorTc);
}
