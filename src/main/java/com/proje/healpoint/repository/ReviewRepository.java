package com.proje.healpoint.repository;

import com.proje.healpoint.model.Appointments;
import com.proje.healpoint.model.Reviews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Reviews,Long> {
    @Query("SELECT AVG(r.points) FROM Reviews r WHERE r.doctor.tc = :doctorTc")
    Double calculateAverageRatingByDoctor(@Param("doctorTc") String doctorTc);
    boolean existsByAppointment(Appointments appointment);
}
