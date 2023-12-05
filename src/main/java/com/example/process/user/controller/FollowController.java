package com.example.process.user.controller;

import com.example.process.security.UserDetailsImpl;
import com.example.process.user.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 사용자의 팔로우/언팔로우 기능과 팔로워, 팔로잉 조회 기능 등
 * Follow 관련 Service 부분
 * */
@RestController
@RequestMapping("/process")
@RequiredArgsConstructor
public class FollowController {
    private final FollowService followService;

    @PostMapping("/follow/{username}")
    public void follow(
            @PathVariable String username,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        followService.follow(username, userDetails.getUser());
    }
}
