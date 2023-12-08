package com.example.process.user.dto;

import com.example.process.user.entity.Follow;
import com.example.process.user.entity.User;
import lombok.Getter;

import java.util.List;
import java.util.function.Function;

@Getter
public class FollowerResponseDto {
    private String followerName;

    public FollowerResponseDto(String followerName) {
        this.followerName = followerName;
    }
}
