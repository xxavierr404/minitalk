package com.xxavierr404.minitalk.services;

import com.xxavierr404.minitalk.dto.UserDTO;
import com.xxavierr404.minitalk.model.Post;
import com.xxavierr404.minitalk.repositories.PostRepository;
import com.xxavierr404.minitalk.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

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
        return postRepository.save(post);
    }
}
