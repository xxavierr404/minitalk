package com.xxavierr404.minitalk.services;

import com.xxavierr404.minitalk.dto.PostDTO;
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
import java.util.stream.Collectors;

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

    public List<PostDTO> getNewsFeed() {
        var user = userRepository.findById(
                ((UserDTO) SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getPrincipal())
                        .getId());
        var posts = new LinkedList<Post>();
        for (var friend : user.get().getFriends()) {
            posts.addAll(postRepository.findAllByAuthorOrderByPostTimeDesc(friend));
        }
        return posts.stream().map(PostDTO::new).collect(Collectors.toList());
    }

    public List<PostDTO> getAll() {
        return postRepository.findAll().stream().map(PostDTO::new).collect(Collectors.toList());
    }

    public void switchLike(Long postId) {
        var user = userRepository.findById(
                ((UserDTO) SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getPrincipal())
                        .getId()).get();
        var post = postRepository.findById(postId).get();
        if (post.getLikes().contains(user)) {
            post.removeLike(user);
        } else {
            post.addLike(user);
        }
        postRepository.save(post);
    }
}
