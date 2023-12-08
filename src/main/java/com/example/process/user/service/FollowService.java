package com.example.process.user.service;

import com.example.process.user.entity.Follow;
import com.example.process.user.entity.User;
import com.example.process.user.repository.FollowRepository;
import com.example.process.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class FollowService {
    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    @Transactional
    public void clickFollow(String followingName, User follower) {
        User following = userRepository.findByUsername(followingName)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        followRepository.findByFollowerIdAndFollowingId(follower.getId(), following.getId())
                .ifPresentOrElse(
                        (follow) -> {
                            follower.removeFollower(follow);
                            following.removeFollowing(follow);
                            followRepository.delete(follow);
                        },
                        () -> {
                            Follow follow = new Follow(follower, following);

                            following.addFollowing(follow);
                            follower.addFollower(follow);

                            followRepository.save(follow);
                        }
                );
    }
}
