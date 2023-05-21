package com.xxavierr404.minitalk.services;

import com.xxavierr404.minitalk.dto.PostDTO;
import com.xxavierr404.minitalk.dto.UserDTO;
import com.xxavierr404.minitalk.model.User;
import com.xxavierr404.minitalk.repositories.ImageLocationRepository;
import com.xxavierr404.minitalk.repositories.PostRepository;
import com.xxavierr404.minitalk.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final ImageLocationRepository imageLocationRepository;

    public void addFriend(Long id) {
        var user = userRepository.findById(
                ((UserDTO) SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getPrincipal())
                        .getId()).get();

        if (user.getId().equals(id)) return;

        user.addFriend(userRepository.findById(id).get());
        userRepository.save(user);
    }

    public void removeFriend(Long id) {
        var user = userRepository.findById(
                ((UserDTO) SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getPrincipal())
                        .getId()).get();

        if (user.getId().equals(id)) return;

        user.removeFriend(userRepository.findById(id).get());
        userRepository.save(user);
    }

    public void setAvatar(Long id) {
        var user = userRepository.findById(
                ((UserDTO) SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getPrincipal())
                        .getId()).get();
        user.setAvatar(imageLocationRepository.findById(id).get());
        userRepository.save(user);
    }

    public UserDTO getUser(Long id) {
        return new UserDTO(userRepository.findById(id).get());
    }

    public List<UserDTO> getUsersByName(String name) {
        var result = new HashSet<>();
        result.addAll(userRepository.getAllByNameContainingIgnoreCase(name));
        result.addAll(userRepository.getAllBySurnameContainingIgnoreCase(name));
        return result.stream().map((e) -> new UserDTO((User) e)).collect(Collectors.toList());
    }

    public List<PostDTO> getPostsFromUser(Long id) {
        return postRepository
                .findAllByAuthorOrderByPostTimeDesc
                        (userRepository.findById(id).get())
                .stream()
                .map(PostDTO::new)
                .collect(Collectors.toList());
    }
}
