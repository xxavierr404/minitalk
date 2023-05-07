package com.xxavierr404.minitalk.services;

import com.xxavierr404.minitalk.model.Post;
import com.xxavierr404.minitalk.repositories.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public Post savePost(Post post) {
        return postRepository.save(post);
    }
}
