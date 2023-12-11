package com.example.process.user;

import lombok.Getter;

@Getter
public class FollowerResponseDto {
    private String followerName;

    public FollowerResponseDto(String followerName) {
        this.followerName = followerName;
    }
}
