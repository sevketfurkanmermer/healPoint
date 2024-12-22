package com.proje.healpoint.service;

import com.proje.healpoint.dto.DtoDoctor;
import com.proje.healpoint.dto.DtoDoctorIU;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface IDoctorService {
    public List<DtoDoctor> getAllDoctors();

    public DtoDoctor getDoctorByToken();

    public void deleteDoctorById();

    public DtoDoctor saveDoctor(DtoDoctorIU doctorForSave);

    public DtoDoctor updateDoctorById(DtoDoctorIU dtoDoctorIU);

    public DtoDoctor getDoctorById(String id);

    public String getDoctorNameFromToken();
}
