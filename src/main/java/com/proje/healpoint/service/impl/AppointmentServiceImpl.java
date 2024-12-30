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
import org.springframework.http.ResponseEntity;
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
        Doctors doctor = doctors.get();
        LocalDate appointmentDate = dtoAppointment.getAppointmentDate();
        LocalTime appointmentTime = dtoAppointment.getAppointmentTime();
        LocalDateTime appointmentDateTime = LocalDateTime.of(appointmentDate, appointmentTime);

        if (appointmentDateTime.isBefore(LocalDateTime.now())) {
            throw new BaseException(
                    new ErrorMessage(MessageType.INVALID_INPUT, "Geçmiş tarih ve saate randevu alınamaz."));
        }

        boolean isAlreadyBooked = appointmentRepository.existsByDoctorAndAppointmentDateAndAppointmentTimeAndAppointmentStatus(
                doctor,
                dtoAppointment.getAppointmentDate(),
                dtoAppointment.getAppointmentTime(),
                AppointmentStatus.AKTIF
        );

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
        dtoDoctor.setName(savedAppointment.getDoctor().getName());
        dtoDoctor.setSurname(savedAppointment.getDoctor().getSurname());
        dtoDoctor.setBranch(savedAppointment.getDoctor().getBranch());
        dtoDoctor.setCity(savedAppointment.getDoctor().getCity());
        dtoDoctor.setEmail(savedAppointment.getDoctor().getEmail());
        response.setDoctor(dtoDoctor);
        response.setPatientTc(savedAppointment.getPatient().getTc());
        DtoPatientReview dtoPatient = new DtoPatientReview();
        dtoPatient.setName(savedAppointment.getPatient().getName());
        dtoPatient.setSurname(savedAppointment.getPatient().getSurname());
        dtoPatient.setGender(savedAppointment.getPatient().getGender());
        response.setPatient(dtoPatient);

        return response;
    }

    public List<DtoAppointment> getUpcomingAppointments() {
        // Şu anki tarih ve zaman
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime twoDaysLater = now.plusDays(2);
        String patientTc = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Appointments> activeAppointments = appointmentRepository.findByPatient_TcAndAppointmentStatus(patientTc, AppointmentStatus.AKTIF);

        List<DtoAppointment> upcomingAppointments = new ArrayList<>();

        for (Appointments appointment : activeAppointments) {
            try {
                LocalDateTime appointmentDateTime = LocalDateTime.of(appointment.getAppointmentDate(), appointment.getAppointmentTime());

                if (appointmentDateTime.isAfter(now) && appointmentDateTime.isBefore(twoDaysLater)) {
                    DtoAppointment dtoAppointment = new DtoAppointment();
                    BeanUtils.copyProperties(appointment, dtoAppointment);
                    if (appointment.getDoctor() != null) {
                        DtoDoctorReview dtoDoctor = new DtoDoctorReview();
                        dtoDoctor.setName(appointment.getDoctor().getName());
                        dtoDoctor.setSurname(appointment.getDoctor().getSurname());
                        dtoDoctor.setBranch(appointment.getDoctor().getBranch());
                        dtoDoctor.setCity(appointment.getDoctor().getCity());
                        dtoDoctor.setEmail(appointment.getDoctor().getEmail());
                        dtoAppointment.setDoctor(dtoDoctor);
                    }
                    if(appointment.getPatient() != null) {
                        DtoPatientReview dtoPatient = new DtoPatientReview();
                        dtoPatient.setName(appointment.getPatient().getName());
                        dtoPatient.setSurname(appointment.getPatient().getSurname());
                        dtoPatient.setGender(appointment.getPatient().getGender());
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

        List<AppointmentStatus> statuses = List.of(AppointmentStatus.TAMAMLANDI, AppointmentStatus.IPTAL);
        List<Appointments> appointmentsList = appointmentRepository.findByPatient_TcAndAppointmentStatusIn(patientTc, statuses);
        List<DtoAppointment> dtoAppointments = new ArrayList<>();

        for (Appointments appointment : appointmentsList) {
            DtoAppointment dto = new DtoAppointment();
            BeanUtils.copyProperties(appointment, dto);
            if (appointment.getDoctor() != null) {
                DtoDoctorReview dtoDoctor = new DtoDoctorReview();
                dtoDoctor.setName(appointment.getDoctor().getName());
                dtoDoctor.setSurname(appointment.getDoctor().getSurname());
                dtoDoctor.setBranch(appointment.getDoctor().getBranch());
                dtoDoctor.setCity(appointment.getDoctor().getCity());
                dtoDoctor.setEmail(appointment.getDoctor().getEmail());
                dto.setDoctor(dtoDoctor);

                DtoPatientReview dtoPatient = new DtoPatientReview();
                dtoPatient.setName(appointment.getPatient().getName());
                dtoPatient.setSurname(appointment.getPatient().getSurname());
                dtoPatient.setGender(appointment.getPatient().getGender());
                dto.setPatient(dtoPatient);
            }
            if (appointment.getPatient() != null) {
                dto.setPatientTc(appointment.getPatient().getTc());
            }

            if (appointment.getDoctor() != null) {
                dto.setDoctorTc(appointment.getDoctor().getTc());
            }
            dtoAppointments.add(dto);
        }
        dtoAppointments.sort(Comparator.comparing(DtoAppointment::getAppointmentDate).thenComparing(DtoAppointment::getAppointmentTime));
        return dtoAppointments;
    }
    @Override
    public List<DtoAppointment> getAllAppointmentsByPatient() {
        String patientTc = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Patients patient = patientRepository.findById(patientTc)
                .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, "Hasta bulunamadı")));

        List<Appointments> appointmentsList = appointmentRepository.findByPatientOrderByAppointmentDateAscAppointmentTimeAsc(patient);

        List<DtoAppointment> dtoAppointments = new ArrayList<>();
        for (Appointments appointment : appointmentsList) {
            DtoAppointment dtoAppointment = new DtoAppointment();
            BeanUtils.copyProperties(appointment, dtoAppointment);

            if (appointment.getDoctor() != null) {
                DtoDoctorReview dtoDoctor = new DtoDoctorReview();
                dtoDoctor.setName(appointment.getDoctor().getName());
                dtoDoctor.setSurname(appointment.getDoctor().getSurname());
                dtoDoctor.setBranch(appointment.getDoctor().getBranch());
                dtoDoctor.setCity(appointment.getDoctor().getCity());
                dtoDoctor.setEmail(appointment.getDoctor().getEmail());
                dtoAppointment.setDoctor(dtoDoctor);
            }
            dtoAppointments.add(dtoAppointment);
        }
        return dtoAppointments;
    }
    @Override
    public List<DtoAppointment> getAllAppointmentsByDoctor() {
        String doctorTc = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Doctors doctor = doctorRepository.findById(doctorTc)
                .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, "Doktor bulunamadı")));

        List<Appointments> appointmentsList = appointmentRepository.findByDoctorOrderByAppointmentDateAscAppointmentTimeAsc(doctor);

        List<DtoAppointment> dtoAppointments = new ArrayList<>();

        for (Appointments appointment : appointmentsList) {
            DtoAppointment dtoAppointment = new DtoAppointment();
            BeanUtils.copyProperties(appointment, dtoAppointment);
            if (appointment.getPatient() != null) {
                DtoPatientReview dtoPatient = new DtoPatientReview();
                dtoPatient.setName(appointment.getPatient().getName());
                dtoPatient.setSurname(appointment.getPatient().getSurname());
                dtoPatient.setGender(appointment.getPatient().getGender());
                dtoAppointment.setPatient(dtoPatient);
            }
            dtoAppointments.add(dtoAppointment);
        }
        return dtoAppointments;
    }
    @Override
    public List<DtoAppointment> getActiveAppointments() {
        String patientTc = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Patients patient = patientRepository.findById(patientTc)
                .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, "Hasta bulunamadı")));
        List<Appointments> activeAppointments = appointmentRepository.findByPatientAndAppointmentStatusOrderByAppointmentDateAscAppointmentTimeAsc(patient, AppointmentStatus.AKTIF);
        List<DtoAppointment> dtoAppointments = new ArrayList<>();
        for (Appointments appointment : activeAppointments) {
            dtoAppointments.add(convertToDto(appointment));
        }
        return dtoAppointments;
    }
    @Override
    public List<DtoAppointment> getDoctorActiveAppointments() {
        String doctorTc = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Doctors doctor = doctorRepository.findById(doctorTc)
                .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, "Doktor bulunamadı")));
        List<Appointments> activeAppointments = appointmentRepository.findByDoctorAndAppointmentStatusOrderByAppointmentDateAscAppointmentTimeAsc(doctor, AppointmentStatus.AKTIF);
        List<DtoAppointment> dtoAppointments = new ArrayList<>();
        for (Appointments appointment : activeAppointments) {
            dtoAppointments.add(convertToDto(appointment));
        }
        return dtoAppointments;
    }
    private DtoAppointment convertToDto(Appointments appointment) {
        DtoAppointment dto = new DtoAppointment();
        dto.setAppointmentId(appointment.getAppointmentId());
        dto.setAppointmentDate(appointment.getAppointmentDate());
        dto.setAppointmentTime(appointment.getAppointmentTime());
        dto.setAppointmentStatus(appointment.getAppointmentStatus());
        dto.setAppointmentText(appointment.getAppointmentText());
        if (appointment.getPatient() != null) {
            DtoPatientReview dtoPatient = new DtoPatientReview();
            dtoPatient.setName(appointment.getPatient().getName());
            dtoPatient.setSurname(appointment.getPatient().getSurname());
            dtoPatient.setGender(appointment.getPatient().getGender());
            dto.setPatient(dtoPatient);
            dto.setPatientTc(appointment.getPatient().getTc());
        }

        if (appointment.getDoctor() != null) {
            DtoDoctorReview dtoDoctor = new DtoDoctorReview();
            dtoDoctor.setName(appointment.getDoctor().getName());
            dtoDoctor.setSurname(appointment.getDoctor().getSurname());
            dtoDoctor.setBranch(appointment.getDoctor().getBranch());
            dtoDoctor.setCity(appointment.getDoctor().getCity());
            dtoDoctor.setEmail(appointment.getDoctor().getEmail());
            dto.setDoctor(dtoDoctor);
        }
        return dto;
    }
    @Override
    public List<DtoAppointment> getDoctorCompletedAndCancelledAppointments() {

        String doctorTc = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Doctors doctor = doctorRepository.findById(doctorTc)
                .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, "Doktor bulunamadı")));

        List<AppointmentStatus> statuses = List.of(AppointmentStatus.TAMAMLANDI, AppointmentStatus.IPTAL);
        List<Appointments> appointmentsList = appointmentRepository.findByDoctorAndAppointmentStatusIn(doctor, statuses);
        List<DtoAppointment> dtoAppointments = new ArrayList<>();
        for (Appointments appointment : appointmentsList) {
            DtoAppointment dto = new DtoAppointment();

            dto.setAppointmentId(appointment.getAppointmentId());
            dto.setAppointmentDate(appointment.getAppointmentDate());
            dto.setAppointmentTime(appointment.getAppointmentTime());
            dto.setAppointmentStatus(appointment.getAppointmentStatus());
            dto.setAppointmentText(appointment.getAppointmentText());

            if (appointment.getPatient() != null) {
                DtoPatientReview dtoPatient = new DtoPatientReview();
                dtoPatient.setName(appointment.getPatient().getName());
                dtoPatient.setSurname(appointment.getPatient().getSurname());
                dtoPatient.setGender(appointment.getPatient().getGender());
                dto.setPatient(dtoPatient);
            }
            dtoAppointments.add(dto);
        }
        dtoAppointments.sort(Comparator.comparing(DtoAppointment::getAppointmentDate)
                .thenComparing(DtoAppointment::getAppointmentTime));
        return dtoAppointments;
    }
    @Override
    public DtoAppointment cancelAppointment(Long appointmentId) {

        String patientTc = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Appointments appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new BaseException(
                        new ErrorMessage(MessageType.NO_RECORD_EXIST, "Randevu bulunamadı")));

        if (!appointment.getPatient().getTc().equals(patientTc)) {
            throw new BaseException(new ErrorMessage(MessageType.UNAUTHORIZED, "Bu randevuyu iptal etme yetkiniz yok"));
        }

        if (appointment.getAppointmentStatus() == AppointmentStatus.IPTAL) {
            throw new BaseException(new ErrorMessage(MessageType.INVALID_INPUT, "Randevu zaten iptal edilmiş veya tamamlanmış"));
        }

        appointment.setAppointmentStatus(AppointmentStatus.IPTAL);
        Appointments updatedAppointment = appointmentRepository.save(appointment);


        return convertToDto(updatedAppointment);
    }
    @Override
    public List<DtoAppointment> cancelAppointmentForDoctor(Long appointmentId) {
        String doctorTc = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Doktoru doğrula
        Doctors doctor = doctorRepository.findById(doctorTc)
                .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, "Doktor bulunamadı")));

        // Randevuyu doğrula
        Appointments appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, "Randevu bulunamadı")));

        // Doktorun bu randevuya erişimi olup olmadığını kontrol et
        if (!appointment.getDoctor().getTc().equals(doctor.getTc())) {
            throw new BaseException(new ErrorMessage(MessageType.UNAUTHORIZED, "Bu randevuyu iptal etme yetkiniz yok."));
        }

        // Randevunun durumunu "İPTAL" olarak güncelle
        appointment.setAppointmentStatus(AppointmentStatus.IPTAL);
        appointmentRepository.save(appointment);

        // Doktorun güncel aktif randevularını getir
        List<Appointments> activeAppointments = appointmentRepository.findByDoctorAndAppointmentStatusOrderByAppointmentDateAscAppointmentTimeAsc(doctor, AppointmentStatus.AKTIF);

        // DTO'ya dönüştür
        return convertToDtoList(activeAppointments);
    }
    private List<DtoAppointment> convertToDtoList(List<Appointments> appointments) {
        List<DtoAppointment> dtoAppointments = new ArrayList<>();
        for (Appointments appointment : appointments) {
            DtoAppointment dto = new DtoAppointment();
            BeanUtils.copyProperties(appointment, dto);

            if (appointment.getDoctor() != null) {
                DtoDoctorReview dtoDoctor = new DtoDoctorReview();
                BeanUtils.copyProperties(appointment.getDoctor(), dtoDoctor);
                dto.setDoctor(dtoDoctor);
            }

            if (appointment.getPatient() != null) {
                DtoPatientReview dtoPatient = new DtoPatientReview();
                BeanUtils.copyProperties(appointment.getPatient(), dtoPatient);
                dto.setPatient(dtoPatient);
            }

            dtoAppointments.add(dto);
        }
        return dtoAppointments;
    }


}
