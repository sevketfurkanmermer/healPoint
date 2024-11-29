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
import java.util.Optional;

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
        return "KAYIT OLUŞTURULDU";

    }

    @Override
    public String updatePatient(String Patient_tc,DtoPatientIU dtoPatientIU) {
        Optional<Patients> optional =  patientRepository.findById(Patient_tc);
        if (optional.isEmpty()) {
            return "KAYIT BULUNAMADI";
        }else {
            Patients existingPatient = optional.get();
            if(dtoPatientIU.getPatient_gender()!=null) {existingPatient.setPatient_gender(dtoPatientIU.getPatient_gender());}
            if (dtoPatientIU.getPatient_name()!=null) {existingPatient.setPatient_name(dtoPatientIU.getPatient_name());}
            if (dtoPatientIU.getPatient_surname() != null) {existingPatient.setPatient_surname(dtoPatientIU.getPatient_surname());}
            if (dtoPatientIU.getPatient_email() != null) {existingPatient.setPatient_email(dtoPatientIU.getPatient_email());}
            if (dtoPatientIU.getPatient_phonenumber() != null) {existingPatient.setPatient_phonenumber(dtoPatientIU.getPatient_phonenumber());}
            patientRepository.save(existingPatient);
            return "KAYIT GÜNCELLENDİ";
        }
    }

    @Override
    public DtoPatient getPatientById(String Patient_tc) {
        Optional<Patients> optional = patientRepository.findById(Patient_tc);
        if (optional.isEmpty()) {
            return new DtoPatient();
        }else {
            Patients existingPatient = optional.get();
            DtoPatient dtoPatient = new DtoPatient();
            BeanUtils.copyProperties(existingPatient, dtoPatient);
            return dtoPatient;
        }
    }


}
