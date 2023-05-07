package com.xxavierr404.minitalk.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCredentialsDTO {
    private String email;
    private String password;

    public static UserCredentialsDTO fromRegisterDTO(UserRegisterDTO dto) {
        var result = new UserCredentialsDTO();
        result.setEmail(dto.getEmail());
        result.setPassword(dto.getPassword());
        return result;
    }
}
