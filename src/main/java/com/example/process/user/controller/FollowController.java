package com.example.process.user.controller;

import com.example.process.user.security.UserDetailsImpl;
import com.example.process.user.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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
    public void getFollowerList(@PathVariable String username) {
        followService.getFollowerList(username);
    }
}
