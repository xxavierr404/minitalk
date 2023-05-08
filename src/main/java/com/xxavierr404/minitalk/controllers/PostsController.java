package com.xxavierr404.minitalk.controllers;

import com.xxavierr404.minitalk.model.Post;
import com.xxavierr404.minitalk.services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PostsController {
    private final PostService postService;

    @PostMapping("/post")
    private ResponseEntity<Long> addNewPost(Post post) {
        return ResponseEntity.ok(postService.savePost(post).getId());
    }
}
