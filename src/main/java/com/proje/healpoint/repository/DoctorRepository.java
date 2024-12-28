package com.proje.healpoint.repository;

import com.proje.healpoint.model.Doctors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface DoctorRepository extends JpaRepository<Doctors,String> {
    @Query("SELECT d FROM Doctors d WHERE " +
            "d.isAccountActive = true AND " +
            "(:city IS NULL OR d.city = :city) AND " +
            "(:district IS NULL OR d.district = :district) AND " +
            "(:branch IS NULL OR d.branch = :branch) AND " +
            "EXISTS (SELECT a FROM Availability a WHERE " +
            "a.doctor = d AND a.dayOfWeek = :dayOfWeek AND " +
            "a.startTime <= :appointmentTime AND a.endTime > :appointmentTime) AND " +
            "NOT EXISTS (SELECT ap FROM Appointments ap WHERE " +
            "ap.doctor = d AND ap.appointmentDate = :appointmentDate AND " +
            "ap.appointmentTime = :appointmentTime AND ap.appointmentStatus = 'AKTIF')"
    )
    List<Doctors> findAvailableDoctors(
            @Param("city") String city,
            @Param("district") String district,
            @Param("branch") String branch,
            @Param("appointmentDate") LocalDate appointmentDate,
            @Param("appointmentTime") LocalTime appointmentTime,
            @Param("dayOfWeek") DayOfWeek dayOfWeek
    );
    @Query("SELECT d FROM Doctors d WHERE d.isAccountActive = true")
    List<Doctors> findAllActiveDoctors();

}
