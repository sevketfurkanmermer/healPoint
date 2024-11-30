package com.proje.healpoint.repository;

import com.proje.healpoint.model.Appointments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointments,Long> {
}
