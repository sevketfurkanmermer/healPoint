package com.proje.healpoint.service.impl;

import com.proje.healpoint.dto.DtoDoctor;
import com.proje.healpoint.dto.DtoDoctorIU;
import com.proje.healpoint.exception.BaseException;
import com.proje.healpoint.exception.ErrorMessage;
import com.proje.healpoint.exception.MessageType;
import com.proje.healpoint.jwt.JwtService;
import com.proje.healpoint.model.Availability;
import com.proje.healpoint.model.Doctors;
import com.proje.healpoint.repository.AvailabilityRepository;
import com.proje.healpoint.repository.DoctorRepository;
import com.proje.healpoint.service.IDoctorService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalTime;
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
    @Autowired
    private AvailabilityRepository availabilityRepository;
    @Override
    public List<DtoDoctor> getAllDoctors() {

        List<DtoDoctor> dtoDoctorList = new ArrayList<>();

        for (Doctors doctor : doctorRepository.findAllActiveDoctors()) {
            DtoDoctor dtoDoctor = new DtoDoctor();
            BeanUtils.copyProperties(doctor, dtoDoctor);
            dtoDoctorList.add(dtoDoctor);
        }

        return dtoDoctorList;
    }

    @Override
    public DtoDoctor getDoctorByToken() {
        String doctorTc = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        DtoDoctor dtoDoctor = new DtoDoctor();
        Doctors doctor = doctorRepository.findById(doctorTc)
                .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, "Doktor bulunamadı")));
        BeanUtils.copyProperties(doctor, dtoDoctor);
        return dtoDoctor;
    }

    @Override
    public DtoDoctor getDoctorById(String id) {
        String tc = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    
        Doctors doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor not found with ID: " + id));
    
        DtoDoctor dtoDoctor = new DtoDoctor();
    
        BeanUtils.copyProperties(doctor, dtoDoctor);
    
        return dtoDoctor;
    }
    
    @Override
    public void deleteDoctorById() {
        String doctorTc = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Doctors> optional =  doctorRepository.findById(doctorTc);
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
        for (DayOfWeek day : List.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY)) {
            Availability availability = new Availability();
            availability.setDoctor(createdDoctor);
            availability.setDayOfWeek(day);
            availability.setStartTime(LocalTime.of(9, 0));
            availability.setEndTime(LocalTime.of(16, 0));
            availabilityRepository.save(availability);
        }
        return dtoDoctor;
    }

    @Override
    public DtoDoctor updateDoctorById(DtoDoctorIU doctorForUpdate) {

        String doctorTc = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        DtoDoctor dtoDoctor = new DtoDoctor();

        Optional<Doctors> optionalDoctor = doctorRepository.findById(doctorTc);

        if (optionalDoctor.isPresent()) {
            Doctors doctor = optionalDoctor.get();
            doctor.setName(doctorForUpdate.getName());
            doctor.setSurname(doctorForUpdate.getSurname());
            doctor.setPhoneNumber(doctorForUpdate.getPhoneNumber());
            doctor.setEmail(doctorForUpdate.getEmail());
            String encodedPassword=passwordEncoder.encode(doctorForUpdate.getPassword());
            doctor.setPassword(encodedPassword);
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


