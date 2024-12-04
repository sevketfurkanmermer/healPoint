package com.proje.healpoint.service.impl;

import com.proje.healpoint.dto.*;
import com.proje.healpoint.exception.BaseException;
import com.proje.healpoint.exception.ErrorMessage;
import com.proje.healpoint.exception.MessageType;
import com.proje.healpoint.model.Appointments;
import com.proje.healpoint.model.Patients;
import com.proje.healpoint.repository.AppointmentRepository;
import com.proje.healpoint.repository.PatientRepository;
import com.proje.healpoint.service.IPatientService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PatientServiceImpl implements IPatientService {

    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public String createPatient(DtoPatientIU dtoPatientIU) {
        Optional<Patients> existingPatient = patientRepository.findExistingPatient(
                dtoPatientIU.getPatientTc(),
                dtoPatientIU.getPatientEmail(),
                dtoPatientIU.getPatientPhonenumber()
        );
        if (existingPatient.isPresent()) {
            Patients patient = existingPatient.get();
            StringBuilder errorMessage = new StringBuilder();
            if (patient.getPatientTc().equals(dtoPatientIU.getPatientTc())) {
                errorMessage.append("[TC: ").append(dtoPatientIU.getPatientTc()).append("] ");
            }
            if (patient.getPatientEmail().equals(dtoPatientIU.getPatientEmail())) {
                errorMessage.append("[Email: ").append(dtoPatientIU.getPatientEmail()).append("] ");
            }
            if (patient.getPatientPhonenumber().equals(dtoPatientIU.getPatientPhonenumber())) {
                errorMessage.append("[Telefon: ").append(dtoPatientIU.getPatientPhonenumber()).append("] ");
            }
            throw new BaseException(new ErrorMessage(MessageType.RECORD_ALREADY_EXIST, errorMessage.toString()));
        }
        Patients patients = new Patients();
        patients.setPatientTc(dtoPatientIU.getPatientTc());
        patients.setPatientEmail(dtoPatientIU.getPatientEmail());
        patients.setPatientPhonenumber(dtoPatientIU.getPatientPhonenumber());
        patients.setPatient_surname(dtoPatientIU.getPatient_surname());
        patients.setPatient_name(dtoPatientIU.getPatient_name());
        patients.setPatient_gender(dtoPatientIU.getPatient_gender());
        String encodedPassword=passwordEncoder.encode(dtoPatientIU.getPatient_password());
        patients.setPatient_password(encodedPassword);
        patientRepository.save(patients);
        return "KAYIT OLUŞTURULDU";
    }
    @Override
    public String updatePatient(String Patient_tc,DtoPatientIU dtoPatientIU) {
        List<String> errorMessages = new ArrayList<>();
        Optional<Patients> optional = patientRepository.findById(Patient_tc);
        if (optional.isEmpty()) {
            throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, Patient_tc));
        }
        if (patientRepository.existsByPatientEmailAndPatientTcNot(dtoPatientIU.getPatientEmail(), Patient_tc)) {
            errorMessages.add("Email: " + dtoPatientIU.getPatientEmail());
        }
        if (patientRepository.existsByPatientPhonenumberAndPatientTcNot(dtoPatientIU.getPatientPhonenumber(), Patient_tc)) {
            errorMessages.add("Telefon: " + dtoPatientIU.getPatientPhonenumber());
        }
        if (!errorMessages.isEmpty()) {
            throw new BaseException(new ErrorMessage(MessageType.RECORD_ALREADY_EXIST, String.join(" - ", errorMessages)));
        }
        Patients existingPatient = optional.get();
        if (dtoPatientIU.getPatient_gender() != null) { existingPatient.setPatient_gender(dtoPatientIU.getPatient_gender()); }
        if (dtoPatientIU.getPatient_name() != null) { existingPatient.setPatient_name(dtoPatientIU.getPatient_name()); }
        if (dtoPatientIU.getPatient_surname() != null) { existingPatient.setPatient_surname(dtoPatientIU.getPatient_surname()); }
        if (dtoPatientIU.getPatientEmail() != null) { existingPatient.setPatientEmail(dtoPatientIU.getPatientEmail()); }
        if (dtoPatientIU.getPatientPhonenumber() != null) { existingPatient.setPatientPhonenumber(dtoPatientIU.getPatientPhonenumber()); }
        patientRepository.save(existingPatient);
        return "KAYIT GÜNCELLENDİ";
        }

        @Override
        public DtoPatient getPatientById (String Patient_tc){
            Optional<Patients> optional = patientRepository.findById(Patient_tc);
            if (optional.isEmpty()){
                throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, Patient_tc));
            }
            else {
                Patients existingPatient = optional.get();
                DtoPatient dtoPatient = new DtoPatient();
                BeanUtils.copyProperties(existingPatient, dtoPatient);
                List<DtoAppointment> dtoAppointments = new ArrayList<>();
                for (Appointments appointment : existingPatient.getAppointments()) {
                    DtoAppointment dtoAppointment = new DtoAppointment();
                    dtoAppointment.setAppointment_id(appointment.getAppointment_id());
                    dtoAppointment.setAppointment_date(appointment.getAppointment_date());
                    dtoAppointment.setAppointment_time(appointment.getAppointment_time());
                    dtoAppointment.setAppointment_status(appointment.getAppointment_status());
                    dtoAppointment.setAppointment_text(appointment.getAppointment_text());
                    dtoAppointment.setPatientTc(dtoPatient.getPatientTc());
                    DtoDoctorReview dtoDoctorReview = new DtoDoctorReview();
                    BeanUtils.copyProperties(appointment.getDoctor(), dtoDoctorReview);
                    dtoAppointment.setDoctor(dtoDoctorReview);
                    dtoAppointments.add(dtoAppointment);

                }

                dtoPatient.setAppointments(dtoAppointments);
                return dtoPatient;

            }
        }
    }




