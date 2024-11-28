package com.proje.healpoint.service.impl;

import com.proje.healpoint.dto.DtoPatient;
import com.proje.healpoint.dto.DtoPatientIU;
import com.proje.healpoint.model.Patients;
import com.proje.healpoint.repository.PatientRepository;
import com.proje.healpoint.service.IPatientService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PatientServiceImpl implements IPatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Override
    public String createPatient(DtoPatientIU dtoPatientIU) {
        Patients patients = new Patients();
        DtoPatient dtoPatient = new DtoPatient();
        BeanUtils.copyProperties(dtoPatientIU, patients);
        Patients createdPatients = patientRepository.save(patients);
        BeanUtils.copyProperties(createdPatients, dtoPatient);
        return "KAYIT OLUŞTURLDU";

    }
}
