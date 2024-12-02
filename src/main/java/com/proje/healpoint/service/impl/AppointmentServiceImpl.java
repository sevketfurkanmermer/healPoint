package com.proje.healpoint.service.impl;

import com.proje.healpoint.controller.IAppointmentController;
import com.proje.healpoint.dto.DtoAppointment;
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
import com.proje.healpoint.service.IAppointmentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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
         List<String> errorMessages = new ArrayList<>();
         Optional<Patients> patients = patientRepository.findById(dtoAppointment.getPatientTc());
         Optional<Doctors> doctors= doctorRepository.findById(dtoAppointment.getDoctorTc());
         if(patients.isEmpty()){errorMessages.add("Hasta Tc: " + dtoAppointment.getPatientTc());}
         if(doctors.isEmpty()){errorMessages.add("Doktor Tc: " + dtoAppointment.getDoctorTc());}
         if (!errorMessages.isEmpty()) {throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST, String.join(" - ", errorMessages)));}
         Appointments appointments = new Appointments();
         BeanUtils.copyProperties(dtoAppointment, appointments);
         appointments.setPatient(patients.get());
         appointments.setDoctor(doctors.get());
         appointmentRepository.save(appointments);
         return "Randevu Başarıyla Oluşturuldu";
    }

    }
