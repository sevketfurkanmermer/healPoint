package com.proje.healpoint.repository;

import com.proje.healpoint.model.DoctorAvailability;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface DoctorAvailabilityRepository extends JpaRepository<DoctorAvailability, Long> {
}
