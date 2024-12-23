package com.proje.healpoint.repository;

import com.proje.healpoint.model.Doctors;
import com.proje.healpoint.model.FavoriteDoctors;
import com.proje.healpoint.model.Patients;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavoriteDoctorsRepository extends JpaRepository<FavoriteDoctors, Long> {
    boolean existsByPatient_TcAndDoctor_Tc(String patientTc, String doctorTc);

    List<FavoriteDoctors> findByPatient_Tc(String patientTc);

    Optional<FavoriteDoctors> findByPatientAndDoctor(Patients patient, Doctors doctor);

    List<FavoriteDoctors> findByPatient(Patients patient);
}
