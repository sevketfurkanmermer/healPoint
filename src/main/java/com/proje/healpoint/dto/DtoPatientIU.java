package com.proje.healpoint.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class DtoPatientIU extends DtoUserIU {
    private LocalDate birthDate;
}
