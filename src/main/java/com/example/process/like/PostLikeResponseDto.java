package com.example.process.like;

import com.example.process.post.Post;
import com.example.process.user.User;
import lombok.Getter;

@Getter
public class PostLikeResponseDto {
    private Long id;
    private Long postId;
    private String username;

    public PostLikeResponseDto(PostLike postLike) {
        this.id = postLike.getId();
        this.postId = postLike.getPost().getId();
        this.username = postLike.getUser().getUsername();
    }
}
