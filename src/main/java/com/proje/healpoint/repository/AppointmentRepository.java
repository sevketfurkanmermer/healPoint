package com.proje.healpoint.repository;

import com.proje.healpoint.enums.AppointmentStatus;
import com.proje.healpoint.model.Appointments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointments,Long> {
    boolean existsByDoctor_TcAndAppointmentDateAndAppointmentTime(String doctorTc, Date appointmentDate, String appointmentTime);

    List<Appointments> findByPatient_TcAndAppointmentStatus(String patientTc, AppointmentStatus status);
}
