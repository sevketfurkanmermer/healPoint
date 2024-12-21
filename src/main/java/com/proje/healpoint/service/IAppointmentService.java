package com.proje.healpoint.service;

import com.proje.healpoint.dto.DtoAppointment;

import java.util.List;

public interface IAppointmentService {

    public DtoAppointment createAppointment(DtoAppointment dtoAppointment);

    public List<DtoAppointment> getUpcomingAppointments();
    public List<DtoAppointment> getCompletedAndCancelledAppointments();
    public List<DtoAppointment> getAllAppointmentsByPatient();
    public List<DtoAppointment> getAllAppointmentsByDoctor();
}
