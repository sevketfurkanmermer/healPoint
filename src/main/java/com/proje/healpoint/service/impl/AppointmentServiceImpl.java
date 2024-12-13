package com.proje.healpoint.service.impl;

import com.proje.healpoint.dto.DtoAppointment;
import com.proje.healpoint.dto.DtoDoctorReview;
import com.proje.healpoint.enums.AppointmentStatus;
import com.proje.healpoint.exception.BaseException;
import com.proje.healpoint.exception.ErrorMessage;
import com.proje.healpoint.exception.MessageType;
import com.proje.healpoint.model.Appointments;
import com.proje.healpoint.model.Doctors;
import com.proje.healpoint.model.Patients;
import com.proje.healpoint.repository.AppointmentRepository;
import com.proje.healpoint.repository.DoctorRepository;
import com.proje.healpoint.repository.PatientRepository;
import com.proje.healpoint.service.IAppointmentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentServiceImpl implements IAppointmentService {
    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Override
    public DtoAppointment createAppointment(DtoAppointment dtoAppointment) {

        String patientTc = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<String> errorMessages = new ArrayList<>();

        Optional<Patients> patients = patientRepository.findById(patientTc);
        Optional<Doctors> doctors = doctorRepository.findById(dtoAppointment.getDoctorTc());

        if (patients.isEmpty()) {
            errorMessages.add("Hasta Tc: " + patientTc);
        }
        if (doctors.isEmpty()) {
            errorMessages.add("Doktor Tc: " + dtoAppointment.getDoctorTc());
        }

        if (!errorMessages.isEmpty()) {
            throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, String.join(" - ", errorMessages)));
        }

        boolean isAlreadyBooked = appointmentRepository.existsByDoctor_TcAndAppointmentDateAndAppointmentTime(
                dtoAppointment.getDoctorTc(),
                dtoAppointment.getAppointmentDate(),
                dtoAppointment.getAppointmentTime());

        if (isAlreadyBooked) {
            throw new BaseException(
                    new ErrorMessage(MessageType.APPOINTMENT_ALREADY_EXISTS,
                            "Bu doktor için belirtilen tarih ve saat zaten dolu."));
        }

        Appointments appointments = new Appointments();
        BeanUtils.copyProperties(dtoAppointment, appointments);
        appointments.setPatient(patients.get());
        appointments.setDoctor(doctors.get());
        Appointments savedAppointment = appointmentRepository.save(appointments);

        DtoAppointment response = new DtoAppointment();
        BeanUtils.copyProperties(savedAppointment, response);

        DtoDoctorReview dtoDoctor = new DtoDoctorReview();
        dtoDoctor.setDoctorName(savedAppointment.getDoctor().getName());
        dtoDoctor.setDoctorSurname(savedAppointment.getDoctor().getSurname());
        dtoDoctor.setBranch(savedAppointment.getDoctor().getBranch());
        dtoDoctor.setCity(savedAppointment.getDoctor().getCity());
        dtoDoctor.setEmail(savedAppointment.getDoctor().getEmail());
        response.setDoctor(dtoDoctor);

        response.setPatientTc(savedAppointment.getPatient().getTc());

        return response;
    }

    public List<DtoAppointment> getUpcomingAppointments() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime twoDaysLater = now.plusDays(2);
        String patientTc = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<Appointments> activeAppointments = appointmentRepository.findByPatient_TcAndAppointmentStatus(patientTc,
                AppointmentStatus.AKTİF);

        List<DtoAppointment> upcomingAppointments = new ArrayList<>();

        for (Appointments appointment : activeAppointments) {
            try {
                LocalDate appointmentDate = appointment.getAppointmentDate().toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate(); //
                LocalTime appointmentTime = LocalTime.parse(appointment.getAppointmentTime());
                LocalDateTime appointmentDateTime = LocalDateTime.of(appointmentDate, appointmentTime);

                if (appointmentDateTime.isBefore(twoDaysLater) && appointmentDateTime.isAfter(now)) {

                    DtoAppointment dtoAppointment = new DtoAppointment();
                    dtoAppointment.setAppointmentId(appointment.getAppointmentId());
                    dtoAppointment.setAppointmentDate(appointment.getAppointmentDate());
                    dtoAppointment.setAppointmentTime(appointment.getAppointmentTime());
                    dtoAppointment.setAppointmentStatus(appointment.getAppointmentStatus());
                    dtoAppointment.setAppointmentText(appointment.getAppointmentText());

                    DtoDoctorReview dtoDoctor = new DtoDoctorReview();
                    dtoDoctor.setDoctorName(appointment.getDoctor().getName());
                    dtoDoctor.setDoctorSurname(appointment.getDoctor().getSurname());
                    dtoDoctor.setBranch(appointment.getDoctor().getBranch());
                    dtoDoctor.setCity(appointment.getDoctor().getCity());
                    dtoDoctor.setEmail(appointment.getDoctor().getEmail());
                    dtoAppointment.setDoctor(dtoDoctor);

                    upcomingAppointments.add(dtoAppointment);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return upcomingAppointments;
    }

}
