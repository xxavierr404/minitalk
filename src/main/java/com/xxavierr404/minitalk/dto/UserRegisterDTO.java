package com.xxavierr404.minitalk.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegisterDTO {
    private String email;
    private String password;
    private String name;
    private String surname;
}
