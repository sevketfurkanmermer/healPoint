package com.proje.healpoint.service.impl;

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
import com.proje.healpoint.repository.ReviewRepository;
import com.proje.healpoint.service.IReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReviewServiceImpl implements IReviewService {
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private AppointmentRepository appointmentRepository;
    @Override
    public String createReview(DtoReview dtoReview) {
        Optional<Appointments> optionalAppointment = appointmentRepository.findById(dtoReview.getAppointment().getAppointment_id());
        if (optionalAppointment.isEmpty()) {
            throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, "Randevu ID: " + dtoReview.getAppointment().getAppointment_id()));
        }
        Appointments appointment = optionalAppointment.get();

        if(!appointment.getAppointment_status().equals(AppointmentStatus.TAMAMLANDI)){
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
            throw new BaseException(new ErrorMessage(MessageType.INVALID_INPUT, "Hasta bilgisi eksik"));
        }
        Doctors doctor = appointment.getDoctor();
        if (doctor != null) {
            review.setDoctor(doctor);
        } else {
            throw new BaseException(new ErrorMessage(MessageType.INVALID_INPUT, ": Doktor bilgisi eksik."));
        }
        reviewRepository.save(review);
        return "Değerlendirme başarıyla oluşturuldu.";

    }
}
