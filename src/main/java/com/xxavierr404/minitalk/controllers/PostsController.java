package com.xxavierr404.minitalk.controllers;

import com.xxavierr404.minitalk.model.Post;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostsController {
    @PostMapping
    private ResponseEntity<Long> addNewPost(Post post) {
        
    }
}
