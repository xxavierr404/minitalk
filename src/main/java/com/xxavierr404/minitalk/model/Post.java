package com.xxavierr404.minitalk.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String text;
    @ManyToOne(fetch = FetchType.EAGER)
    private ImageLocation attachment;
    @ManyToOne
    private User author;
    private LocalDateTime postTime;
    @ManyToMany
    private List<User> likes;

    public void addLike(User user) {
        likes.add(user);
    }

    public void removeLike(User user) {
        likes.remove(user);
    }
}
