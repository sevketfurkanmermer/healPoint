package com.proje.healpoint.repository;

import com.proje.healpoint.model.Patients;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patients, String> {
    @Query("SELECT p FROM Patients p WHERE p.tc = :patientTc " +
            "OR p.email = :patientEmail " +
            "OR p.phoneNumber = :patientPhonenumber")
    Optional<Patients> findExistingPatient(@Param("patientTc") String patientTc,
                                           @Param("patientEmail") String patientEmail,
                                           @Param("patientPhonenumber") String patientPhonenumber);

    boolean existsByEmailAndTcNot(String patientEmail, String patientTc);
    boolean existsByPhoneNumberAndTcNot(String patientPhoneNumber, String patientTc);
    Optional <Patients >findByPhoneNumber(String username);
}
