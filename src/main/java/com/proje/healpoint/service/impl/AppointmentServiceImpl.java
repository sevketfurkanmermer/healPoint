package com.proje.healpoint.service.impl;

import com.proje.healpoint.controller.IAppointmentController;
import com.proje.healpoint.dto.DtoAppointment;
import com.proje.healpoint.model.Appointments;
import com.proje.healpoint.model.Doctors;
import com.proje.healpoint.model.Patients;
import com.proje.healpoint.repository.AppointmentRepository;
import com.proje.healpoint.repository.DoctorRepository;
import com.proje.healpoint.repository.PatientRepository;
import com.proje.healpoint.service.IAppointmentService;
import com.proje.healpoint.service.IAppointmentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public String createAppointment(DtoAppointment dtoAppointment) {

        Optional<Patients> patients = patientRepository.findById(dtoAppointment.getPatient().getPatient_tc());
        Optional<Doctors> doctors= doctorRepository.findById(dtoAppointment.getDoctor().getDoctor_tc());
        if(patients.isPresent() && doctors.isPresent()){
            Appointments appointments = new Appointments();
            BeanUtils.copyProperties(dtoAppointment, appointments);
            appointments.setPatient(patients.get());
            appointments.setDoctor(doctors.get());
            appointmentRepository.save(appointments);
            return "Randevu Başarıyla Oluşturuldu";
        }else {
            return "Hasta ya da Doktor bulunamadı";
        }

    }
}
