package com.proje.healpoint.service.impl;

import com.proje.healpoint.model.Appointments;
import com.proje.healpoint.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class ReminderScheduler {
    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private MailSenderService mailSenderService;

    @Scheduled(cron = "0 0 * * * ?")
    public void sendUpcomingAppointmentNotifications() {

            LocalDate twoDaysLater = LocalDate.now().plusDays(2);

            List<Appointments> upcomingAppointments = appointmentRepository.findByAppointmentDateAndReminderSentFalse(twoDaysLater);

            for (Appointments appointment : upcomingAppointments) {
                String email = appointment.getPatient().getEmail();
                String subject = "Randevu Hatırlatması";
                String body = String.format("Merhaba %s,\n\n%s tarihinde saat %s'de %s bölümünde doktorunuz %s ile randevunuz var.\n\nSağlıklı Günler\n\nHealPoint.",
                        appointment.getPatient().getName(),
                        appointment.getAppointmentDate(),
                        appointment.getAppointmentTime(),
                        appointment.getDoctor().getBranch(),
                        appointment.getDoctor().getName() + " " + appointment.getDoctor().getSurname());

                mailSenderService.sendMail(email, subject, body);

                appointment.setReminderSent(true);
                appointmentRepository.save(appointment);
            }
        }
    }

