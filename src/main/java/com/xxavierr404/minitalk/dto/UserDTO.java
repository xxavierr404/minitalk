package com.xxavierr404.minitalk.dto;

import com.xxavierr404.minitalk.model.User;
import lombok.Getter;

import java.util.List;

@Getter
public class UserDTO {
    private final String name;
    private final String surname;
    private final List<User> friends;

    public UserDTO(User user) {
        this.name = user.getName();
        this.surname = user.getSurname();
        this.friends = user.getFriends();
    }
}
