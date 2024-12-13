package com.proje.healpoint.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proje.healpoint.model.DoctorAvailability;

@Repository
public interface DoctorAvailabilityRepository extends JpaRepository<DoctorAvailability,Long> {

    public DoctorAvailability findByDoctor_Tc(String doctorTC);
    
}
