package com.proje.healpoint.repository;

import com.proje.healpoint.model.Appointments;
import com.proje.healpoint.model.Doctors;
import com.proje.healpoint.model.Patients;
import com.proje.healpoint.model.Reviews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Reviews,Long> {
    @Query("SELECT AVG(r.points) FROM Reviews r WHERE r.doctor.tc = :doctorTc")
    Double calculateAverageRatingByDoctor(@Param("doctorTc") String doctorTc);
    boolean existsByAppointment(Appointments appointment);
    List<Reviews> findByDoctor(Doctors doctor);
    List<Reviews> findByPatient(Patients patient);
}
