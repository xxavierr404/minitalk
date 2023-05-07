package com.xxavierr404.minitalk.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UserRegisterDTO {
    private String email;
    private String password;
    private String name;
    private String surname;
    private LocalDate birthdate;
    private String city;
    private String university;
}
