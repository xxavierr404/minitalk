package com.xxavierr404.minitalk.services;

import com.xxavierr404.minitalk.dto.UserDTO;
import com.xxavierr404.minitalk.model.Post;
import com.xxavierr404.minitalk.repositories.PostRepository;
import com.xxavierr404.minitalk.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public Post savePost(Post post) {
        var user = userRepository.findById(
                ((UserDTO) SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getPrincipal())
                        .getId());
        post.setAuthor(user.get());
        post.setPostTime(LocalDateTime.now());
        return postRepository.save(post);
    }

    public List<Post> getNewsFeed() {
        var user = userRepository.findById(
                ((UserDTO) SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getPrincipal())
                        .getId());
        var posts = new LinkedList<Post>();
        for (var friend: user.get().getFriends()) {
            posts.addAll(postRepository.findAllByAuthorOrderByPostTimeDesc(friend));
        }
        return posts;
    }

    public List<Post> getAll() {
        return postRepository.findAll();
    }
}
