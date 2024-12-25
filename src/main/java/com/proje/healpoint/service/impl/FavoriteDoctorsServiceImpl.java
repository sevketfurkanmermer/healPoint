package com.proje.healpoint.service.impl;

import com.proje.healpoint.dto.DtoDoctorsFav;
import com.proje.healpoint.exception.BaseException;
import com.proje.healpoint.exception.ErrorMessage;
import com.proje.healpoint.exception.MessageType;
import com.proje.healpoint.model.Doctors;
import com.proje.healpoint.model.FavoriteDoctors;
import com.proje.healpoint.model.Patients;
import com.proje.healpoint.repository.DoctorRepository;
import com.proje.healpoint.repository.FavoriteDoctorsRepository;
import com.proje.healpoint.repository.PatientRepository;
import com.proje.healpoint.service.IFavoriteDoctorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FavoriteDoctorsServiceImpl implements IFavoriteDoctorsService {
        @Autowired
        private FavoriteDoctorsRepository favoriteDoctorsRepository;
        @Autowired
        private DoctorRepository doctorRepository;
        @Autowired
        private PatientRepository patientRepository;

        @Override
        public String addFavoriteDoctor(String doctorTc) {
                String patientTc = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                Patients patient = patientRepository.findById(patientTc)
                                .orElseThrow(() -> new BaseException(
                                                new ErrorMessage(MessageType.NO_RECORD_EXIST, "Hasta bulunamadı")));

                Doctors doctor = doctorRepository.findById(doctorTc)
                                .orElseThrow(() -> new BaseException(
                                                new ErrorMessage(MessageType.NO_RECORD_EXIST, "Doktor bulunamadı")));
                if (favoriteDoctorsRepository.existsByPatient_TcAndDoctor_Tc(patientTc, doctorTc)) {
                        throw new BaseException(new ErrorMessage(MessageType.RECORD_ALREADY_EXIST,
                                        "Bu doktor zaten favorilerinizde."));
                }
                FavoriteDoctors favorite = new FavoriteDoctors();
                favorite.setPatient(patient);
                favorite.setDoctor(doctor);
                favoriteDoctorsRepository.save(favorite);
                return "Doktor favorilere eklendi.";
        }

        @Override
        public List<DtoDoctorsFav> getFavoriteDoctors() {
                String patientTc = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                List<FavoriteDoctors> favorites = favoriteDoctorsRepository.findByPatient_Tc(patientTc);
                return favorites.stream()
                                .map(favorite -> convertToDto(favorite.getDoctor()))
                                .collect(Collectors.toList());
        }

        private DtoDoctorsFav convertToDto(Doctors doctor) {
                DtoDoctorsFav dto = new DtoDoctorsFav();
                dto.setTc(doctor.getTc());
                dto.setName(doctor.getName());
                dto.setSurname(doctor.getSurname());
                dto.setBranch(doctor.getBranch());
                dto.setCity(doctor.getCity());
                dto.setAvgPoint(doctor.getAvgPoint());
                dto.setEmail(doctor.getEmail());
                return dto;
        }

        @Override
        public List<DtoDoctorsFav> removeFavoriteDoctor(String doctorTc) {
                String patientTc = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

                Patients patient = patientRepository.findById(patientTc)
                                .orElseThrow(() -> new BaseException(
                                                new ErrorMessage(MessageType.NO_RECORD_EXIST, "Hasta bulunamadı")));

                Doctors doctor = doctorRepository.findById(doctorTc)
                                .orElseThrow(() -> new BaseException(
                                                new ErrorMessage(MessageType.NO_RECORD_EXIST, "Doktor bulunamadı")));

                FavoriteDoctors favorite = favoriteDoctorsRepository.findByPatientAndDoctor(patient, doctor)
                                .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST,
                                                "Bu doktor favorilerinizde değil.")));

                favoriteDoctorsRepository.delete(favorite);

                List<FavoriteDoctors> updatedFavorites = favoriteDoctorsRepository.findByPatient(patient);
                return updatedFavorites.stream()
                                .map(fav -> convertToDto(fav.getDoctor()))
                                .collect(Collectors.toList());
        }
}
