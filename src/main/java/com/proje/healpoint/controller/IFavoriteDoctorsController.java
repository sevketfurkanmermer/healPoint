package com.proje.healpoint.controller;

import com.proje.healpoint.dto.DtoDoctorsFav;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface IFavoriteDoctorsController {
    public ResponseEntity<String> addFavoriteDoctor(@PathVariable String doctorTc);
    public ResponseEntity<List<DtoDoctorsFav>> getFavoriteDoctors();
}
