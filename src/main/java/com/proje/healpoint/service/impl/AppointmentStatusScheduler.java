package com.proje.healpoint.service.impl;

import com.proje.healpoint.enums.AppointmentStatus;
import com.proje.healpoint.model.Appointments;
import com.proje.healpoint.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Component
public class AppointmentStatusScheduler {
    @Autowired
    private AppointmentRepository appointmentRepository;

    @Scheduled(cron = "59 * * * * ?") // Her saatin 59. dakikasında çalışır
    public void updateCompletedAppointments() {
        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now();

        List<Appointments> activeAppointments = appointmentRepository.findByAppointmentStatusAndAppointmentDateAndAppointmentTimeBefore(
                AppointmentStatus.AKTİF, today, now);

        for (Appointments appointment : activeAppointments) {
            appointment.setAppointmentStatus(AppointmentStatus.TAMAMLANDI);
            appointmentRepository.save(appointment);
        }
    }
}
