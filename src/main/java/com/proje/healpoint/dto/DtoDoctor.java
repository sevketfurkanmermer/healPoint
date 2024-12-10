package com.proje.healpoint.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DtoDoctor extends DtoUser {

    private String branch;
    private String about;
    private String city;
    private String district;
    private String address;
    private double avgPoint;


}
