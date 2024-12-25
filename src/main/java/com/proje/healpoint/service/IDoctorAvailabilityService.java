package com.proje.healpoint.service;

import java.sql.Date;
import java.time.LocalDate;

import com.proje.healpoint.dto.DtoDoctorAvailability;

public interface IDoctorAvailabilityService {

    public DtoDoctorAvailability getDoctorAvailability(String doctorId,LocalDate date);
}
