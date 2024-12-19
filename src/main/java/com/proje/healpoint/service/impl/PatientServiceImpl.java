package com.proje.healpoint.service.impl;

import com.proje.healpoint.dto.*;
import com.proje.healpoint.exception.BaseException;
import com.proje.healpoint.exception.ErrorMessage;
import com.proje.healpoint.exception.MessageType;
import com.proje.healpoint.jwt.JwtService;
import com.proje.healpoint.model.Appointments;
import com.proje.healpoint.model.Patients;
import com.proje.healpoint.repository.AppointmentRepository;
import com.proje.healpoint.repository.PatientRepository;
import com.proje.healpoint.service.IPatientService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
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
    @Autowired
    private JwtService jwtService;

    @Override
    public String createPatient(DtoPatientIU dtoPatientIU) {
        Optional<Patients> existingPatient = patientRepository.findExistingPatient(
                dtoPatientIU.getEmail(),
                dtoPatientIU.getTc(),
                dtoPatientIU.getPhoneNumber());
        if (existingPatient.isPresent()) {
            Patients patient = existingPatient.get();
            StringBuilder errorMessage = new StringBuilder();
            if (patient.getTc().equals(dtoPatientIU.getTc())) {
                errorMessage.append("[TC: ").append(dtoPatientIU.getTc()).append("] ");
            }
            if (patient.getEmail().equals(dtoPatientIU.getEmail())) {
                errorMessage.append("[Email: ").append(dtoPatientIU.getEmail()).append("] ");
            }
            if (patient.getPhoneNumber().equals(dtoPatientIU.getPhoneNumber())) {
                errorMessage.append("[Telefon: ").append(dtoPatientIU.getPhoneNumber()).append("] ");
            }
            throw new BaseException(new ErrorMessage(MessageType.RECORD_ALREADY_EXIST, errorMessage.toString()));
        }
        Patients patients = new Patients();
        patients.setTc(dtoPatientIU.getTc());
        patients.setEmail(dtoPatientIU.getEmail());
        patients.setPhoneNumber(dtoPatientIU.getPhoneNumber());
        patients.setSurname(dtoPatientIU.getSurname());
        patients.setName(dtoPatientIU.getName());
        patients.setGender(dtoPatientIU.getGender());
        patients.setBirthDate(dtoPatientIU.getBirthDate());
        String encodedPassword = passwordEncoder.encode(dtoPatientIU.getPassword());
        patients.setPassword(encodedPassword);
        patientRepository.save(patients);
        return "KAYIT OLUŞTURULDU";
    }

    @Override
    public DtoPatient updatePatient(DtoPatientIU dtoPatientIU) {

        // SecurityContextHolder ile hasta TC bilgisini al
        String patientTc = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println("PatientTc in service: " + patientTc);

        Optional<Patients> optional = patientRepository.findById(patientTc);
        if (optional.isEmpty()) {
            throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, patientTc));
        }

        List<String> errorMessages = new ArrayList<>();

        if (patientRepository.existsByEmailAndTcNot(dtoPatientIU.getEmail(), patientTc)) {
            errorMessages.add("Email: " + dtoPatientIU.getEmail());
        }
        if (patientRepository.existsByPhoneNumberAndTcNot(dtoPatientIU.getPhoneNumber(), patientTc)) {
            errorMessages.add("Telefon: " + dtoPatientIU.getPhoneNumber());
        }
        if (!errorMessages.isEmpty()) {
            throw new BaseException(
                    new ErrorMessage(MessageType.RECORD_ALREADY_EXIST, String.join(" - ", errorMessages)));
        }

        Patients existingPatient = optional.get();
        if (dtoPatientIU.getGender() != null) {
            existingPatient.setGender(dtoPatientIU.getGender());
        }
        if (dtoPatientIU.getName() != null) {
            existingPatient.setName(dtoPatientIU.getName());
        }
        if (dtoPatientIU.getSurname() != null) {
            existingPatient.setSurname(dtoPatientIU.getSurname());
        }
        if (dtoPatientIU.getEmail() != null) {
            existingPatient.setEmail(dtoPatientIU.getEmail());
        }
        if (dtoPatientIU.getPhoneNumber() != null) {
            existingPatient.setPhoneNumber(dtoPatientIU.getPhoneNumber());
        }
        if (dtoPatientIU.getBirthDate() != null) {
            existingPatient.setBirthDate(dtoPatientIU.getBirthDate());
        }

        patientRepository.save(existingPatient);

        DtoPatient dtoPatient = new DtoPatient();
        BeanUtils.copyProperties(existingPatient, dtoPatient);
        return dtoPatient;
    }

    @Override
    public DtoPatient getPatientById() {
        String patientTc = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Patients> optional = patientRepository.findById(patientTc);
        if (optional.isEmpty()) {
            throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, patientTc));
        } else {
            Patients existingPatient = optional.get();
            DtoPatient dtoPatient = new DtoPatient();
            BeanUtils.copyProperties(existingPatient, dtoPatient);
            List<DtoAppointment> dtoAppointments = new ArrayList<>();
            for (Appointments appointment : existingPatient.getAppointments()) {
                DtoAppointment dtoAppointment = new DtoAppointment();
                dtoAppointment.setAppointmentId(appointment.getAppointmentId());
                dtoAppointment.setAppointmentDate(appointment.getAppointmentDate());
                dtoAppointment.setAppointmentTime(appointment.getAppointmentTime());
                dtoAppointment.setAppointmentStatus(appointment.getAppointmentStatus());
                dtoAppointment.setAppointmentText(appointment.getAppointmentText());
                dtoAppointment.setPatientTc(dtoPatient.getTc());
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
