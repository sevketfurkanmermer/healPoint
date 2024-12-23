package com.proje.healpoint.controller.impl;

import com.proje.healpoint.controller.IFavoriteDoctorsController;
import com.proje.healpoint.dto.DtoDoctorsFav;
import com.proje.healpoint.service.IFavoriteDoctorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/favorites")
public class FavoriteDoctorsController implements IFavoriteDoctorsController {
    @Autowired
    private IFavoriteDoctorsService favoriteDoctorsService;

    @PostMapping("/add/{doctorTc}")
    @Override
    public ResponseEntity<String> addFavoriteDoctor(String doctorTc) {
        String response = favoriteDoctorsService.addFavoriteDoctor(doctorTc);
        return ResponseEntity.ok(response);

    }

    @GetMapping(path = "/list")
    @Override
    public ResponseEntity<List<DtoDoctorsFav>> getFavoriteDoctors() {
        List<DtoDoctorsFav> favoriteDoctors = favoriteDoctorsService.getFavoriteDoctors();
        return ResponseEntity.ok(favoriteDoctors);
    }

    @DeleteMapping("/remove/{doctorTc}")
    @Override
    public ResponseEntity<List<DtoDoctorsFav>> removeFavoriteDoctor(@PathVariable String doctorTc) {
        List<DtoDoctorsFav> updatedFavorites = favoriteDoctorsService.removeFavoriteDoctor(doctorTc);
        return ResponseEntity.ok(updatedFavorites);
    }
}
