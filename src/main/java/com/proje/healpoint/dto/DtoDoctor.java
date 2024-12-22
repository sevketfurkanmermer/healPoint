package com.proje.healpoint.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

import com.proje.healpoint.model.Reviews;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DtoDoctor extends DtoUser {
    private String tc;
    private String branch;
    private String about;
    private String city;
    private String district;
    private String address;
    private double avgPoint;
    private List<Reviews> reviews;
}
