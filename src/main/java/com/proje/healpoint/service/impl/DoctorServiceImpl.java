package com.proje.healpoint.service.impl;

import com.proje.healpoint.dto.DtoDoctor;
import com.proje.healpoint.dto.DtoDoctorIU;
import com.proje.healpoint.exception.BaseException;
import com.proje.healpoint.exception.ErrorMessage;
import com.proje.healpoint.exception.MessageType;
import com.proje.healpoint.jwt.JwtService;
import com.proje.healpoint.model.Doctors;
import com.proje.healpoint.repository.DoctorRepository;
import com.proje.healpoint.service.IDoctorService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DoctorServiceImpl implements IDoctorService {

    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private JwtService jwtService;
    @Override
    public List<DtoDoctor> getAllDoctors() {
        String Tc = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
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
        Doctors doctor = new Doctors();
        doctor.setTc(dtoDoctorIU.getTc());
        doctor.setEmail(dtoDoctorIU.getEmail());
        doctor.setPhoneNumber(dtoDoctorIU.getPhoneNumber());
        doctor.setSurname(dtoDoctorIU.getSurname());
        doctor.setName(dtoDoctorIU.getName());
        doctor.setGender(dtoDoctorIU.getGender());
        doctor.setBranch(dtoDoctorIU.getBranch());
        doctor.setAbout(dtoDoctorIU.getAbout());
        doctor.setCity(dtoDoctorIU.getCity());
        doctor.setAddress(dtoDoctorIU.getAddress());
        doctor.setDistrict(dtoDoctorIU.getDistrict());
        String encodedPassword=passwordEncoder.encode(dtoDoctorIU.getPassword());
        doctor.setPassword(encodedPassword);
        Doctors createdDoctor = doctorRepository.save(doctor);
        DtoDoctor dtoDoctor=new DtoDoctor();
        BeanUtils.copyProperties(createdDoctor,dtoDoctor);
        return dtoDoctor;
    }

    @Override
    public DtoDoctor updateDoctor(String id, DtoDoctorIU doctorForUpdate) {

        DtoDoctor dtoDoctor = new DtoDoctor();

        Optional<Doctors> optionalDoctor = doctorRepository.findById(id);

        if (optionalDoctor.isPresent()) {
            Doctors doctor = optionalDoctor.get();
            doctor.setName(doctorForUpdate.getName());
            doctor.setSurname(doctorForUpdate.getSurname());
            doctor.setPhoneNumber(doctorForUpdate.getPhoneNumber());
            doctor.setEmail(doctorForUpdate.getEmail());
            doctor.setPassword(doctorForUpdate.getPassword());
            doctor.setCity(doctorForUpdate.getCity());
            doctor.setDistrict(doctorForUpdate.getDistrict());
            doctor.setAddress(doctorForUpdate.getAddress());
            doctor.setAbout(doctorForUpdate.getAbout());
            doctor.setGender(doctorForUpdate.getGender());
            Doctors updatedDoctor = doctorRepository.save(doctor);
            BeanUtils.copyProperties(updatedDoctor,dtoDoctor);
            return dtoDoctor;
        }

        return null;
    }

    @Override
    public String getDoctorNameFromToken() {
        String doctorTc = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Doctors doctor = doctorRepository.findById(doctorTc)
                .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, "Doktor bulunamadı")));
        return doctor.getName();
    }
}


