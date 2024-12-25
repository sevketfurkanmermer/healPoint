package com.proje.healpoint.service.impl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.proje.healpoint.dto.DtoDoctorAvailability;
import com.proje.healpoint.exception.BaseException;
import com.proje.healpoint.exception.ErrorMessage;
import com.proje.healpoint.exception.MessageType;
import com.proje.healpoint.model.Doctors;
import com.proje.healpoint.repository.AppointmentRepository;
import com.proje.healpoint.repository.DoctorRepository;
import com.proje.healpoint.service.IDoctorAvailabilityService;

@Service
public class DoctorAvailabilityServiceImpl implements IDoctorAvailabilityService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    public DtoDoctorAvailability getDoctorAvailability(String doctorId, LocalDate date) {
        String tc = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Doctors doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new BaseException(
                        new ErrorMessage(MessageType.NO_RECORD_EXIST, doctorId)));

        List<LocalTime> availableTimes = getAvailableTimes(doctor, date);

        DtoDoctorAvailability dto = new DtoDoctorAvailability();
        dto.setAvailableDate(date);
        dto.setAvailableTimes(availableTimes);

        return dto;
    }

    public List<LocalTime> getAvailableTimes(Doctors doctor, LocalDate date) {
        LocalTime start = LocalTime.of(9, 0);
        LocalTime end = LocalTime.of(18, 0);

        List<LocalTime> availableTimes = new ArrayList<>();
        boolean hasAppointments = appointmentRepository.existsByDoctorAndAppointmentDate(doctor, date);

        if (!hasAppointments) {
            while (!start.isAfter(end)) { 
                availableTimes.add(start);
                start = start.plusHours(1);
            }
            return availableTimes;
        }

        while (!start.isAfter(end)) {
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
