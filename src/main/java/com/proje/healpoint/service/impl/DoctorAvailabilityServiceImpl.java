package com.proje.healpoint.service.impl;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
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


    public DtoDoctorAvailability getDoctorAvailability(String doctorId, Date date) {
        Doctors doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new BaseException(
                    new ErrorMessage(MessageType.NO_RECORD_EXIST,doctorId)));
    
        List<LocalTime> availableTimes = getAvailableTimes(doctor, date);
    
        DtoDoctorAvailability dto = new DtoDoctorAvailability();
        dto.setDoctorTC(doctorId);
        dto.setAvailableDate(date);
        dto.setAvailableTimes(availableTimes);
    
        return dto;
    }

    public List<LocalTime> getAvailableTimes(Doctors doctor, Date date) {
        LocalTime start = doctor.getWorkingHoursStart();
        LocalTime end = doctor.getWorkingHoursEnd();
        List<LocalTime> availableTimes = new ArrayList<>();

        while (start.isBefore(end)) {
            if (isSlotAvailable(doctor, date, start.toString())) {
                availableTimes.add(start);
            }
            start = start.plusHours(1);
        }
        return availableTimes;
    }

    private boolean isSlotAvailable(Doctors doctor, Date date, String time) {
        return !appointmentRepository.existsByDoctorAndAppointmentDateAndAppointmentTime(doctor, date, time);
    }

}
