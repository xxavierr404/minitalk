package com.xxavierr404.minitalk.dto;

import com.xxavierr404.minitalk.model.ImageLocation;
import com.xxavierr404.minitalk.model.User;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class UserDTO {
    private final Long id;
    private final String name;
    private final String surname;
    private final List<Long> friends;
    private final LocalDate birthdate;
    private final String city;
    private final String university;
    private final ImageLocation avatar;

    public UserDTO(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.surname = user.getSurname();
        this.friends = user.getFriends().stream().map(User::getId).collect(Collectors.toList());
        this.birthdate = user.getBirthdate();
        this.city = user.getCity();
        this.university = user.getUniversity();
        this.avatar = user.getAvatar();
    }
}
