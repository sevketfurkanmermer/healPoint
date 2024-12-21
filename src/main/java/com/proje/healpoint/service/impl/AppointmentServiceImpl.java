package com.proje.healpoint.service.impl;

import com.proje.healpoint.dto.DtoAppointment;
import com.proje.healpoint.dto.DtoDoctorReview;
import com.proje.healpoint.dto.DtoPatientReview;
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
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        DtoPatientReview dtoPatient = new DtoPatientReview();
        dtoPatient.setPatientName(savedAppointment.getPatient().getName());
        dtoPatient.setPatientSurname(savedAppointment.getPatient().getSurname());
        dtoPatient.setPatientGender(savedAppointment.getPatient().getGender());
        response.setPatient(dtoPatient);

        return response;
    }

    public List<DtoAppointment> getUpcomingAppointments() {
        // Şu anki tarih ve zaman
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime twoDaysLater = now.plusDays(2);
        String patientTc = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Appointments> activeAppointments = appointmentRepository.findByPatient_TcAndAppointmentStatus(patientTc, AppointmentStatus.AKTİF);

        List<DtoAppointment> upcomingAppointments = new ArrayList<>();

        for (Appointments appointment : activeAppointments) {
            try {
                LocalDateTime appointmentDateTime = LocalDateTime.of(appointment.getAppointmentDate(), appointment.getAppointmentTime());

                if (appointmentDateTime.isAfter(now) && appointmentDateTime.isBefore(twoDaysLater)) {
                    DtoAppointment dtoAppointment = new DtoAppointment();
                    BeanUtils.copyProperties(appointment, dtoAppointment);
                    if (appointment.getDoctor() != null) {
                        DtoDoctorReview dtoDoctor = new DtoDoctorReview();
                        dtoDoctor.setDoctorName(appointment.getDoctor().getName());
                        dtoDoctor.setDoctorSurname(appointment.getDoctor().getSurname());
                        dtoDoctor.setBranch(appointment.getDoctor().getBranch());
                        dtoDoctor.setCity(appointment.getDoctor().getCity());
                        dtoDoctor.setEmail(appointment.getDoctor().getEmail());
                        dtoAppointment.setDoctor(dtoDoctor);
                    }
                    if(appointment.getPatient() != null) {
                        DtoPatientReview dtoPatient = new DtoPatientReview();
                        dtoPatient.setPatientName(appointment.getPatient().getName());
                        dtoPatient.setPatientSurname(appointment.getPatient().getSurname());
                        dtoPatient.setPatientGender(appointment.getPatient().getGender());
                        dtoAppointment.setPatient(dtoPatient);
                    }
                    dtoAppointment.setPatientTc(patientTc);
                    upcomingAppointments.add(dtoAppointment);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        upcomingAppointments.sort(Comparator.comparing(DtoAppointment::getAppointmentDate).thenComparing(DtoAppointment::getAppointmentTime));

        return upcomingAppointments;
    }

    public List<DtoAppointment> getCompletedAndCancelledAppointments() {
        String patientTc = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Patients patient = patientRepository.findById(patientTc)
                .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, "Hasta bulunamadı")));

        List<AppointmentStatus> statuses = List.of(AppointmentStatus.TAMAMLANDI, AppointmentStatus.İPTAL);
        List<Appointments> appointmentsList = appointmentRepository.findByPatient_TcAndAppointmentStatusIn(patientTc, statuses);
        List<DtoAppointment> dtoAppointments = new ArrayList<>();

        for (Appointments appointment : appointmentsList) {
            DtoAppointment dto = new DtoAppointment();
            BeanUtils.copyProperties(appointment, dto);
            if (appointment.getDoctor() != null) {
                DtoDoctorReview dtoDoctor = new DtoDoctorReview();
                dtoDoctor.setDoctorName(appointment.getDoctor().getName());
                dtoDoctor.setDoctorSurname(appointment.getDoctor().getSurname());
                dtoDoctor.setBranch(appointment.getDoctor().getBranch());
                dtoDoctor.setCity(appointment.getDoctor().getCity());
                dtoDoctor.setEmail(appointment.getDoctor().getEmail());
                dto.setDoctor(dtoDoctor);

                DtoPatientReview dtoPatient = new DtoPatientReview();
                dtoPatient.setPatientName(appointment.getPatient().getName());
                dtoPatient.setPatientSurname(appointment.getPatient().getSurname());
                dtoPatient.setPatientGender(appointment.getPatient().getGender());
                dto.setPatient(dtoPatient);
            }
            if (appointment.getPatient() != null) {
                dto.setPatientTc(appointment.getPatient().getTc());
            }
            dtoAppointments.add(dto);
        }
        dtoAppointments.sort(Comparator.comparing(DtoAppointment::getAppointmentDate).thenComparing(DtoAppointment::getAppointmentTime));
        return dtoAppointments;
    }

}
