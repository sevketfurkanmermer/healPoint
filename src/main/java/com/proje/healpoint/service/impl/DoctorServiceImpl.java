package com.proje.healpoint.service.impl;

import com.proje.healpoint.dto.DtoDoctor;
import com.proje.healpoint.dto.DtoDoctorIU;
import com.proje.healpoint.model.Doctors;
import com.proje.healpoint.repository.DoctorRepository;
import com.proje.healpoint.service.IDoctorService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DoctorServiceImpl implements IDoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Override
    public List<DtoDoctor> getAllDoctors() {
        List<DtoDoctor> dtoDoctorList = new ArrayList<>();
        for (Doctors doctor : doctorRepository.findAll()) {
            DtoDoctor dtoDoctor = new DtoDoctor();
            BeanUtils.copyProperties(doctor,dtoDoctor);
            dtoDoctorList.add(dtoDoctor);
        }
        return dtoDoctorList;
    }

    @Override
    public DtoDoctor getDoctorById(String id) {

        DtoDoctor dtoDoctor = new DtoDoctor();

        Optional<Doctors> doctorOptional = doctorRepository.findById(id);
        if (doctorOptional.isEmpty()) {
            return null;
        }

        Doctors doctor = doctorOptional.get();
        BeanUtils.copyProperties(doctor,dtoDoctor);

        return dtoDoctor;
    }

    @Override
    public void deleteDoctorById(String id) {
        Optional<Doctors> optional =  doctorRepository.findById(id);
        if (optional.isPresent()) {
            doctorRepository.delete(optional.get());
        }
    }

    @Override
    public DtoDoctor saveDoctor(DtoDoctorIU dtoDoctorIU) {
        DtoDoctor dtoDoctor = new DtoDoctor();
        Doctors doctor = new Doctors();

        BeanUtils.copyProperties(dtoDoctorIU,doctor);
        Doctors savedDoctor = doctorRepository.save(doctor);
        BeanUtils.copyProperties(savedDoctor,dtoDoctor);
        return dtoDoctor;
    }

    @Override
    public DtoDoctor updateDoctor(String id, DtoDoctorIU doctorForUpdate) {

        DtoDoctor dtoDoctor = new DtoDoctor();

        Optional<Doctors> optionalDoctor = doctorRepository.findById(id);

        if (optionalDoctor.isPresent()) {
            Doctors doctor = optionalDoctor.get();
            doctor.setDoctor_name(doctorForUpdate.getDoctor_name());
            doctor.setDoctor_surname(doctorForUpdate.getDoctor_surname());
            doctor.setDoctor_phonenumber(doctorForUpdate.getDoctor_phonenumber());
            doctor.setDoctor_email(doctorForUpdate.getDoctor_email());
            doctor.setDoctor_password(doctorForUpdate.getDoctor_password());
            doctor.setCity(doctorForUpdate.getCity());
            doctor.setDistrict(doctorForUpdate.getDistrict());
            doctor.setAddress(doctorForUpdate.getAddress());
            Doctors updatedDoctor = doctorRepository.save(doctor);
            BeanUtils.copyProperties(updatedDoctor,dtoDoctor);
            return dtoDoctor;
        }

        return null;
    }
}


