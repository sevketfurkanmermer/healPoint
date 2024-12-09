package com.proje.healpoint.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.Period;
import java.util.Date;

@Data

public class DtoPatientIU extends DtoUserIU{
    private Date birthDate;
    public String getAge() {
        if (birthDate == null) {
            return null;
        }
        LocalDate birthLocalDate = new java.sql.Date(birthDate.getTime()).toLocalDate();
        int age = Period.between(birthLocalDate, LocalDate.now()).getYears();
        return String.valueOf(age);
    }
}
