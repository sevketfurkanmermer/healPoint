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
public class DtoDoctor {

    private String Doctor_name;
    private String Doctor_surname;
    private String Doctor_phonenumber;
    private String Doctor_email;
    private String city;
    private String district;
    private String address;

}
