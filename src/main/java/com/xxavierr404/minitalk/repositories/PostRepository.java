package com.xxavierr404.minitalk.repositories;

import com.xxavierr404.minitalk.model.Post;
import com.xxavierr404.minitalk.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByAuthorOrderByPostTimeDesc(User user);
}
