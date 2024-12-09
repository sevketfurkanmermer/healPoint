package com.proje.healpoint.dto;

import com.proje.healpoint.model.Appointments;
import com.proje.healpoint.model.Doctors;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DtoPatient  extends DtoUser{
    private String Tc;
    private Date birthDate;
    private String age;
    private List<DtoAppointment> appointments = new ArrayList<>();


    //private List<DtoReview> reviews;
}
