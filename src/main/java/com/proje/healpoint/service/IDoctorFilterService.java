package com.proje.healpoint.service;

import com.proje.healpoint.dto.DtoDoctor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface IDoctorFilterService {
    public List<DtoDoctor> filterDoctors(String city, String district, String branch, LocalDate date, LocalTime time);
}
