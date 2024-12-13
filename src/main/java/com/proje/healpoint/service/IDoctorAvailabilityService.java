package com.proje.healpoint.service;

import java.sql.Date;

import com.proje.healpoint.dto.DtoDoctorAvailability;
import com.proje.healpoint.dto.DtoDoctorAvailabilityIU;

public interface IDoctorAvailabilityService {

    public DtoDoctorAvailability getDoctorAvailability(Date date);
    public DtoDoctorAvailability saveDoctorAvailability(DtoDoctorAvailabilityIU doctorAvailabilityIU);
    public DtoDoctorAvailability updateDoctorWorkTimes(Long id, DtoDoctorAvailabilityIU doctorAvailabilityIU);
}
