package com.proje.healpoint.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DtoUserIU {
    private String password;
    private String name;
    private String surname;
    private String email;
    private String phoneNumber;
    private String gender;
    private String tc;
}
