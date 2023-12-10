package com.example.process.user;

import lombok.Getter;

@Getter
public class UserResponseDto {
    private String username;
    public UserResponseDto(User user) {
        this.username = user.getUsername();
    }
}