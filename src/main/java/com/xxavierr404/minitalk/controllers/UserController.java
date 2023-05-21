package com.xxavierr404.minitalk.controllers;

import com.xxavierr404.minitalk.dto.PostDTO;
import com.xxavierr404.minitalk.dto.UserDTO;
import com.xxavierr404.minitalk.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@CrossOrigin
public class UserController {
    private final UserService userService;

    @PostMapping("/addFriend")
    private void addFriend(Long id) {
        userService.addFriend(id);
    }

    @PostMapping("/removeFriend")
    private void removeFriend(Long id) {
        userService.removeFriend(id);
    }

    @PostMapping("/setAvatar")
    private void setAvatar(Long id) {
        userService.setAvatar(id);
    }

    @GetMapping("/{id}")
    private UserDTO getUserInfo(@PathVariable Long id) {
        return userService.getUser(id);
    }

    @GetMapping("/{id}/posts")
    private List<PostDTO> getPosts(@PathVariable Long id) {
        return userService.getPostsFromUser(id);
    }

    @GetMapping("/findByName")
    private List<UserDTO> getUsersByName(@RequestParam("name") String name) {
        return userService.getUsersByName(name);
    }
}
