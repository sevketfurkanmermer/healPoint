package com.proje.healpoint.service;

import com.proje.healpoint.dto.DtoDoctor;
import com.proje.healpoint.dto.DtoDoctorIU;

import java.util.List;

public interface IDoctorService {
    public List<DtoDoctor> getAllDoctors();
    public DtoDoctor getDoctorById(String id);
    public void deleteDoctorById(String id);
    public DtoDoctor saveDoctor(DtoDoctorIU doctorForSave);
    public DtoDoctor updateDoctor(String id,DtoDoctorIU doctorForUpdate);
    public String getDoctorNameFromToken();
}
