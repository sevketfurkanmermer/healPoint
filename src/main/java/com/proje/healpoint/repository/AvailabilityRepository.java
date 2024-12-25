package com.proje.healpoint.repository;

import com.proje.healpoint.model.Availability;
import com.proje.healpoint.model.Doctors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.util.List;

@Repository
public interface AvailabilityRepository extends JpaRepository<Availability, Long> {
    List<Availability> findByDoctorAndDayOfWeek(Doctors doctor, DayOfWeek dayOfWeek);

}
