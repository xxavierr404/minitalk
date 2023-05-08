package com.xxavierr404.minitalk.controllers;

import com.xxavierr404.minitalk.model.Post;
import com.xxavierr404.minitalk.services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostsController {
    private final PostService postService;

    @PostMapping
    private ResponseEntity<Long> addNewPost(Post post) {
        return ResponseEntity.ok(postService.savePost(post).getId());
    }

    @GetMapping("/feed")
    private List<Post> getFeed() {
        return postService.getNewsFeed();
    }

    @GetMapping("/test")
    private List<Post> getAll() {
        return postService.getAll();
    }
}
