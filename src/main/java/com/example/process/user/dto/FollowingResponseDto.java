package com.example.process.user.dto;

import lombok.Getter;

@Getter
public class FollowingResponseDto {
    private String followingName;

    public FollowingResponseDto(String followingName) {
        this.followingName = followingName;
    }
}
