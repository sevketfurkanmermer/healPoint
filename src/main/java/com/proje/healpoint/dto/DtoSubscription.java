package com.proje.healpoint.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DtoSubscription {
    private Long id;

    private Long planId;

    private String planName;

    private LocalDate startDate;

    private LocalDate endDate;

    private Boolean isActive;
}
