package com.proje.healpoint.service;


import com.proje.healpoint.dto.DtoDoctorsFav;

import java.util.List;

public interface IFavoriteDoctorsService {
    public String addFavoriteDoctor(String doctorTc) ;
    public List<DtoDoctorsFav> getFavoriteDoctors();


}
