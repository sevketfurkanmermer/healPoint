package com.proje.healpoint.service.impl;

import com.proje.healpoint.dto.DtoAppointmentReview;
import com.proje.healpoint.dto.DtoDoctorReview;
import com.proje.healpoint.dto.DtoPatientReview;
import com.proje.healpoint.dto.DtoReview;
import com.proje.healpoint.enums.AppointmentStatus;
import com.proje.healpoint.exception.BaseException;
import com.proje.healpoint.exception.ErrorMessage;
import com.proje.healpoint.exception.MessageType;
import com.proje.healpoint.model.Appointments;
import com.proje.healpoint.model.Doctors;
import com.proje.healpoint.model.Patients;
import com.proje.healpoint.model.Reviews;
import com.proje.healpoint.repository.AppointmentRepository;
import com.proje.healpoint.repository.DoctorRepository;
import com.proje.healpoint.repository.ReviewRepository;
import com.proje.healpoint.service.IReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReviewServiceImpl implements IReviewService {
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private DoctorRepository doctorRepository;
    @Override
    public DtoReview createReview(DtoReview dtoReview) {
        // Token'dan hasta TC'sini çek
        String patientTc = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();


        // Randevu kontrolü
        Optional<Appointments> optionalAppointment = appointmentRepository.findById(dtoReview.getAppointment().getAppointment_id());
        if (optionalAppointment.isEmpty()) {
            throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, "Randevu ID: " + dtoReview.getAppointment().getAppointment_id()));
        }
        Appointments appointment = optionalAppointment.get();

        boolean reviewExists = reviewRepository.existsByAppointment(appointment);
        if (reviewExists) {
            throw new BaseException(new ErrorMessage(MessageType.REVIEW_ALREADY_EXISTS, null));
        }

        if (!appointment.getPatient().getTc().equals(patientTc)) {
            throw new BaseException(new ErrorMessage(MessageType.UNAUTHORIZED, null));
        }

        if (!appointment.getAppointmentStatus().equals(AppointmentStatus.TAMAMLANDI)) {
            throw new BaseException(new ErrorMessage(MessageType.NOT_COMPLETED_APPOINTMENT, "Randevu ID: " + dtoReview.getAppointment().getAppointment_id()));
        }

        Reviews review = new Reviews();
        review.setPoints(dtoReview.getPoints());
        review.setComments(dtoReview.getComments());
        review.setAppointment(appointment);

        Patients patient = appointment.getPatient();
        if (patient != null) {
            review.setPatient(patient);
        } else {
            throw new BaseException(new ErrorMessage(MessageType.INVALID_INPUT, null));
        }

        Doctors doctor = appointment.getDoctor();
        if (doctor != null) {
            review.setDoctor(doctor);
        } else {
            throw new BaseException(new ErrorMessage(MessageType.INVALID_INPUT, null));
        }

        Reviews savedReview = reviewRepository.save(review);

        double averageRating = reviewRepository.calculateAverageRatingByDoctor(doctor.getTc());
        doctor.setAvgPoint(averageRating);
        doctorRepository.save(doctor);

        DtoReview response = new DtoReview();
        response.setReview_id(savedReview.getReview_id());
        response.setComments(savedReview.getComments());
        response.setPoints(savedReview.getPoints());

        DtoAppointmentReview dtoAppointmentReview = new DtoAppointmentReview();
        dtoAppointmentReview.setAppointment_id(appointment.getAppointmentId());
        dtoAppointmentReview.setAppointment_date(appointment.getAppointmentDate());
        dtoAppointmentReview.setAppointment_time(appointment.getAppointmentTime());
        dtoAppointmentReview.setAppointment_status(appointment.getAppointmentStatus());
        dtoAppointmentReview.setAppointment_text(appointment.getAppointmentText());

        DtoDoctorReview dtoDoctorReview = new DtoDoctorReview();
        dtoDoctorReview.setDoctorName(doctor.getName());
        dtoDoctorReview.setDoctorSurname(doctor.getSurname());
        dtoDoctorReview.setBranch(doctor.getBranch());
        dtoDoctorReview.setCity(doctor.getCity());
        dtoDoctorReview.setEmail(doctor.getEmail());
        dtoAppointmentReview.setDtoDoctorReview(dtoDoctorReview);

        DtoPatientReview dtoPatientReview = new DtoPatientReview();
        dtoPatientReview.setPatient_gender(patient.getGender());
        dtoPatientReview.setPatient_name(patient.getName());
        dtoPatientReview.setPatient_surname(patient.getSurname());
        dtoAppointmentReview.setDtoPatientReview(dtoPatientReview);


        response.setAppointment(dtoAppointmentReview);

        return response;
    }

}
