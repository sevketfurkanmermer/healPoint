package com.proje.healpoint.service;

import com.proje.healpoint.dto.DtoAppointment;

import java.util.List;

public interface IAppointmentService {

    public DtoAppointment createAppointment(DtoAppointment dtoAppointment);

    public List<DtoAppointment> getUpcomingAppointments();
    public List<DtoAppointment> getCompletedAndCancelledAppointments();
    public List<DtoAppointment> getAllAppointmentsByPatient();
    public List<DtoAppointment> getAllAppointmentsByDoctor();
    public List<DtoAppointment> getActiveAppointments();
    public List<DtoAppointment> getDoctorActiveAppointments();
    public List<DtoAppointment> getDoctorCompletedAndCancelledAppointments();
    public DtoAppointment cancelAppointment(Long appointmentId);
}
