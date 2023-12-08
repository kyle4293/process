package com.example.process.user.controller;

import com.example.process.user.dto.FollowerResponseDto;
import com.example.process.user.dto.FollowingResponseDto;
import com.example.process.user.entity.Follow;
import com.example.process.user.security.UserDetailsImpl;
import com.example.process.user.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 사용자의 팔로우/언팔로우 기능과 팔로워, 팔로잉 조회 기능 등
 * Follow 관련 Service 부분
 * */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class FollowController {
    private final FollowService followService;

    @PostMapping("/follow/{username}")
    public void clickFollow(
            @PathVariable String username,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        followService.clickFollow(username, userDetails.getUser());
    }

    @GetMapping("/{username}/followers")
    public ResponseEntity<List<FollowerResponseDto>> getFollowerList(@PathVariable String username) {
        List<FollowerResponseDto> followers = followService.getFollowerList(username);
        return ResponseEntity.ok(followers);
    }

    @GetMapping("/{username}/followings")
    public ResponseEntity<List<FollowingResponseDto>> getFollowingList(@PathVariable String username) {
        List<FollowingResponseDto> followings = followService.getFollowingList(username);
        return ResponseEntity.ok(followings);
    }
}
