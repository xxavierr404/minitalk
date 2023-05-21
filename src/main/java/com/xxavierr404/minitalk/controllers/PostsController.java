package com.xxavierr404.minitalk.controllers;

import com.xxavierr404.minitalk.dto.PostDTO;
import com.xxavierr404.minitalk.model.Post;
import com.xxavierr404.minitalk.services.FileLocationService;
import com.xxavierr404.minitalk.services.PostService;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
@CrossOrigin
public class PostsController {
    private final PostService postService;
    private final FileLocationService fileLocationService;

    @PostMapping
    private ResponseEntity<Long> addNewPost(Post post, @Nullable @RequestParam MultipartFile image) throws Exception {
        if (image != null) {
            var uploadedImage = fileLocationService.save(image.getBytes());
            post.setAttachment(uploadedImage);
        }
        return ResponseEntity.ok(postService.savePost(post).getId());
    }

    @GetMapping("/feed")
    private List<PostDTO> getFeed() {
        return postService.getNewsFeed();
    }

    @GetMapping("/test")
    private List<PostDTO> getAll() {
        return postService.getAll();
    }

    @GetMapping("/{id}/like")
    private void likePost(@PathVariable("id") Long id) {
        postService.switchLike(id);
    }
}
