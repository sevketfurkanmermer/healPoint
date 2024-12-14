package com.proje.healpoint.service.impl;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.proje.healpoint.dto.DtoDoctorAvailability;
import com.proje.healpoint.dto.DtoDoctorAvailabilityIU;
import com.proje.healpoint.exception.BaseException;
import com.proje.healpoint.exception.ErrorMessage;
import com.proje.healpoint.exception.MessageType;
import com.proje.healpoint.model.DoctorAvailability;
import com.proje.healpoint.model.Doctors;
import com.proje.healpoint.repository.AppointmentRepository;
import com.proje.healpoint.repository.DoctorAvailabilityRepository;
import com.proje.healpoint.repository.DoctorRepository;
import com.proje.healpoint.service.IDoctorAvailabilityService;

@Service
public class DoctorAvailabilityServiceImpl implements IDoctorAvailabilityService {

    @Autowired
    private DoctorAvailabilityRepository repository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Override
    public DtoDoctorAvailability saveDoctorAvailability(DtoDoctorAvailabilityIU doctorAvailabilityIU) {
        String tc = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Doctors doctor = doctorRepository.findById(tc)
                .orElseThrow(() -> new BaseException(
                        new ErrorMessage(MessageType.NO_RECORD_EXIST, "Doctor not found: " + tc)));

        Optional<DoctorAvailability> existingAvailability = Optional.ofNullable(repository.findByDoctor_Tc(tc));

        if (existingAvailability.isPresent()) {
            throw new BaseException(new ErrorMessage(
                    MessageType.RECORD_ALREADY_EXIST,
                    "Availability record already exists. Please use update instead."));
        }

        DoctorAvailability availability = new DoctorAvailability();
        BeanUtils.copyProperties(doctorAvailabilityIU, availability);
        availability.setDoctor(doctor);

        DoctorAvailability savedAvailability = repository.save(availability);

        DtoDoctorAvailability dtoDoctorAvailability = new DtoDoctorAvailability();
        BeanUtils.copyProperties(savedAvailability, dtoDoctorAvailability);

        return dtoDoctorAvailability;
    }

    @Override
    public DtoDoctorAvailability updateDoctorWorkTimes(Long id, DtoDoctorAvailabilityIU doctorAvailabilityIU) {

        String tc = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        DtoDoctorAvailability dtoDoctorAvailability = new DtoDoctorAvailability();

        Optional<DoctorAvailability> optionalAvailability = repository.findById(id);

        if (!optionalAvailability.isPresent()) {
            throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, id.toString()));
        }

        DoctorAvailability availability = optionalAvailability.get();
        BeanUtils.copyProperties(doctorAvailabilityIU, availability);
        DoctorAvailability updatedAvailability = repository.save(availability);
        BeanUtils.copyProperties(updatedAvailability, dtoDoctorAvailability);
        return dtoDoctorAvailability;

    }

    public DtoDoctorAvailability getDoctorAvailability(String doctorId,LocalDate date) {
        String tc = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Doctors doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new BaseException(
                        new ErrorMessage(MessageType.NO_RECORD_EXIST, doctorId)));

        DoctorAvailability availability = doctor.getAvailability();
        if (availability == null) {
            throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, "Doctor availability not found"));
        }
        List<LocalTime> availableTimes = getAvailableTimes(doctor, date);

        DtoDoctorAvailability dto = new DtoDoctorAvailability();
        dto.setAvailableDate(date);
        dto.setAvailableTimes(availableTimes);
        dto.setWorkingHoursStart(availability.getWorkingHoursStart());
        dto.setWorkingHoursEnd(availability.getWorkingHoursEnd());

        return dto;
    }

    public List<LocalTime> getAvailableTimes(Doctors doctor, LocalDate date) {
        DoctorAvailability availability = doctor.getAvailability();
        if (availability == null) {
            throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, "Doctor availability not found"));
        }

        LocalTime start = availability.getWorkingHoursStart();
        LocalTime end = availability.getWorkingHoursEnd();

        List<LocalTime> availableTimes = new ArrayList<>();
        boolean hasAppointments = appointmentRepository.existsByDoctorAndAppointmentDate(doctor, date);
        if (!hasAppointments) {
            while (start.isBefore(end)) {
                availableTimes.add(start);
                start = start.plusHours(1);
            }
            return availableTimes;
        }

        while (start.isBefore(end)) {
            if (isSlotAvailable(doctor, date, start)) {
                availableTimes.add(start);
            }
            start = start.plusHours(1);
        }

        return availableTimes;
    }

    private boolean isSlotAvailable(Doctors doctor, LocalDate date, LocalTime time) {
        return !appointmentRepository.existsByDoctorAndAppointmentDateAndAppointmentTime(doctor, date, time);
    }
    
}
