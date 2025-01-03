package com.proje.healpoint.repository;

import com.proje.healpoint.enums.AppointmentStatus;
import com.proje.healpoint.model.Appointments;
import com.proje.healpoint.model.Doctors;

import com.proje.healpoint.model.Patients;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointments, Long> {
        boolean existsByDoctorAndAppointmentDateAndAppointmentTimeAndAppointmentStatus(
                Doctors doctor,
                LocalDate appointmentDate,
                LocalTime appointmentTime,
                AppointmentStatus appointmentStatus
        );
        List<Appointments> findByPatient_TcAndAppointmentStatus(String patientTc, AppointmentStatus status);

        boolean existsByDoctorAndAppointmentDate(Doctors doctor, LocalDate appointmentDate);

        boolean existsByDoctorAndAppointmentDateAndAppointmentTime(Doctors doctor, LocalDate appointmentDate,
                        LocalTime appointmentTime);

        List<Appointments> findByPatient_TcAndAppointmentStatusIn(String patientTc, List<AppointmentStatus> statuses);

        List<Appointments> findByAppointmentStatusAndAppointmentDateAndAppointmentTimeBefore(
                AppointmentStatus status, LocalDate date, LocalTime time);
        List<Appointments> findByPatientOrderByAppointmentDateAscAppointmentTimeAsc(Patients patient);
        List<Appointments> findByDoctorOrderByAppointmentDateAscAppointmentTimeAsc(Doctors doctor);
        List<Appointments> findByPatientAndAppointmentStatusOrderByAppointmentDateAscAppointmentTimeAsc(Patients patient, AppointmentStatus status);
        List<Appointments> findByDoctorAndAppointmentStatusOrderByAppointmentDateAscAppointmentTimeAsc(Doctors doctor, AppointmentStatus status);
        List<Appointments> findByDoctorAndAppointmentStatusIn(Doctors doctor, List<AppointmentStatus> statuses);
        List<Appointments> findByAppointmentDateAndReminderSentFalse(LocalDate appointmentDate);

}

