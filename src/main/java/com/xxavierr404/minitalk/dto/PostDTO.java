package com.xxavierr404.minitalk.dto;

import com.xxavierr404.minitalk.model.ImageLocation;
import com.xxavierr404.minitalk.model.Post;
import com.xxavierr404.minitalk.model.User;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class PostDTO {
    private Long id;
    private String text;
    private ImageLocation attachment;
    private User author;
    private LocalDateTime postTime;
    private List<Long> likes;

    public PostDTO(Post post) {
        this.id = post.getId();
        this.text = post.getText();
        this.attachment = post.getAttachment();
        this.author = post.getAuthor();
        this.postTime = post.getPostTime();
        this.likes = post.getLikes().stream().map(User::getId).collect(Collectors.toList());
    }
}
