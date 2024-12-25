package com.proje.healpoint.service.impl;

import com.proje.healpoint.dto.DtoDoctor;
import com.proje.healpoint.model.Doctors;
import com.proje.healpoint.repository.DoctorRepository;
import com.proje.healpoint.service.IDoctorFilterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DoctorFilterServiceImpl implements IDoctorFilterService {
    @Autowired
    private DoctorRepository doctorRepository;

    @Override
    public List<DtoDoctor> filterDoctors(String city, String district, String branch, LocalDate date, LocalTime time) {

        String patientTc = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        DayOfWeek dayOfWeek = date.getDayOfWeek();

        List<Doctors> doctors = doctorRepository.findAvailableDoctors(city, district, branch, date, time, dayOfWeek);

        return doctors.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private DtoDoctor convertToDto(Doctors doctor) {
        DtoDoctor dto = new DtoDoctor();
        dto.setName(doctor.getName());
        dto.setSurname(doctor.getSurname());
        dto.setBranch(doctor.getBranch());
        dto.setCity(doctor.getCity());
        dto.setDistrict(doctor.getDistrict());
        dto.setAvgPoint(doctor.getAvgPoint());
        dto.setAbout(doctor.getAbout());
        dto.setEmail(doctor.getEmail());
        dto.setGender(doctor.getGender());
        dto.setAddress(doctor.getAddress());
        dto.setEmail(doctor.getEmail());
        return dto;
    }
}
