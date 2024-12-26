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
import com.proje.healpoint.repository.PatientRepository;
import com.proje.healpoint.repository.ReviewRepository;
import com.proje.healpoint.service.IReviewService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReviewServiceImpl implements IReviewService {
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private PatientRepository patientRepository;
    
    @Override
    public DtoReview createReview(DtoReview dtoReview) {
        String patientTc = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Optional<Appointments> optionalAppointment = appointmentRepository.findById(dtoReview.getAppointment().getAppointmentId());
        if (optionalAppointment.isEmpty()) {
            throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, "Randevu ID: " + dtoReview.getAppointment().getAppointmentId()));
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
            throw new BaseException(new ErrorMessage(MessageType.NOT_COMPLETED_APPOINTMENT, "Randevu ID: " + dtoReview.getAppointment().getAppointmentId()));
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
        dtoAppointmentReview.setAppointmentId(appointment.getAppointmentId());
        dtoAppointmentReview.setAppointment_date(appointment.getAppointmentDate());
        dtoAppointmentReview.setAppointment_time(appointment.getAppointmentTime());
        dtoAppointmentReview.setAppointment_status(appointment.getAppointmentStatus());
        dtoAppointmentReview.setAppointment_text(appointment.getAppointmentText());

        DtoDoctorReview dtoDoctorReview = new DtoDoctorReview();
        dtoDoctorReview.setName(doctor.getName());
        dtoDoctorReview.setSurname(doctor.getSurname());
        dtoDoctorReview.setBranch(doctor.getBranch());
        dtoDoctorReview.setCity(doctor.getCity());
        dtoDoctorReview.setEmail(doctor.getEmail());
        dtoAppointmentReview.setDtoDoctorReview(dtoDoctorReview);

        DtoPatientReview dtoPatientReview = new DtoPatientReview();
        dtoPatientReview.setGender(patient.getGender());
        dtoPatientReview.setName(patient.getName());
        dtoPatientReview.setSurname(patient.getSurname());
        dtoAppointmentReview.setDtoPatientReview(dtoPatientReview);

        DtoPatientReview patientDto = new DtoPatientReview();
        patientDto.setName(patient.getName());
        patientDto.setSurname(patient.getSurname());
        patientDto.setGender(patient.getGender());

        response.setPatient(patientDto);


        response.setAppointment(dtoAppointmentReview);

        return response;
    }

    @Override
    public List<DtoReview> getDoctorReviews(String doctorTc) {

        Doctors doctor = doctorRepository.findById(doctorTc)
                .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, "Doktor bulunamadı")));

        List<Reviews> reviewsList = reviewRepository.findByDoctor(doctor);

        List<DtoReview> dtoReviews = new ArrayList<>();
        for (Reviews review : reviewsList) {
            DtoReview dtoReview = new DtoReview();
            BeanUtils.copyProperties(review, dtoReview);

            DtoAppointmentReview dtoAppointment = new DtoAppointmentReview();
            dtoAppointment.setAppointmentId(review.getAppointment().getAppointmentId());
            dtoAppointment.setAppointment_date(review.getAppointment().getAppointmentDate());
            dtoAppointment.setAppointment_time(review.getAppointment().getAppointmentTime());
            dtoAppointment.setAppointment_status(review.getAppointment().getAppointmentStatus());
            dtoAppointment.setAppointment_text(review.getAppointment().getAppointmentText());

            DtoPatientReview dtoPatient = new DtoPatientReview();
            dtoPatient.setName(review.getPatient().getName());
            dtoPatient.setSurname(review.getPatient().getSurname());
            dtoPatient.setGender(review.getPatient().getGender());
            dtoAppointment.setDtoPatientReview(dtoPatient);

            dtoReview.setAppointment(dtoAppointment);
            dtoReview.setPatient(dtoPatient);
            dtoReviews.add(dtoReview);
        }

        return dtoReviews;
    }
    
    @Override
    public List<DtoReview> getPatientReviews() {
        String patientTc = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Patients patient = patientRepository.findById(patientTc)
                .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, "Hasta bulunamadı")));
        List<Reviews> reviews = reviewRepository.findByPatient(patient);

        List<DtoReview> dtoReviews = new ArrayList<>();
        for (Reviews review : reviews) {
            DtoReview dto = new DtoReview();
            BeanUtils.copyProperties(review, dto);

            if (review.getAppointment() != null) {
                DtoAppointmentReview dtoAppointment = new DtoAppointmentReview();
                dtoAppointment.setAppointmentId(review.getAppointment().getAppointmentId());
                dtoAppointment.setAppointment_date(review.getAppointment().getAppointmentDate());
                dtoAppointment.setAppointment_time(review.getAppointment().getAppointmentTime());
                dtoAppointment.setAppointment_status(review.getAppointment().getAppointmentStatus());
                dtoAppointment.setAppointment_text(review.getAppointment().getAppointmentText());

                if (review.getDoctor() != null) {
                    DtoDoctorReview dtoDoctor = new DtoDoctorReview();
                    dtoDoctor.setName(review.getDoctor().getName());
                    dtoDoctor.setSurname(review.getDoctor().getSurname());
                    dtoDoctor.setBranch(review.getDoctor().getBranch());
                    dtoDoctor.setCity(review.getDoctor().getCity());
                    dtoDoctor.setEmail(review.getDoctor().getEmail());
                    dtoAppointment.setDtoDoctorReview(dtoDoctor);
                }
                dto.setAppointment(dtoAppointment);
            }
            dtoReviews.add(dto);
        }
        return dtoReviews;
    }
}
