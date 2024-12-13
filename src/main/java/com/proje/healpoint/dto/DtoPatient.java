package com.proje.healpoint.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.Period;
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

    @Transient
    @JsonProperty("age")
    public Integer getAge() {
        if (birthDate == null) {
            return null;
        }
        LocalDate birthLocalDate = new java.sql.Date(birthDate.getTime()).toLocalDate();
        return Period.between(birthLocalDate, LocalDate.now()).getYears();
    }


    //private List<DtoReview> reviews;
}
