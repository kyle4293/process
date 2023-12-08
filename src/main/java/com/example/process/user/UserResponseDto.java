package com.example.process.user;

import com.example.process.post.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
public class UserResponseDto {
    private String username;
    public UserResponseDto(User user) {
        this.username = user.getUsername();
    }
}
