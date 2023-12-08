package com.example.process.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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

    public List<FollowerResponseDto> getFollowerList(String username) {
        User user = findUser(username);
        List<Follow> followers = user.getFollowers();
        return followers.stream()
                .map(follower -> new FollowerResponseDto(follower.getFollower().getUsername()))
                .collect(Collectors.toList());
    }

    public List<FollowingResponseDto> getFollowingList(String username) {
        User user = findUser(username);
        List<Follow> followers = user.getFollowings();
        return followers.stream()
                .map(following -> new FollowingResponseDto(following.getFollowing().getUsername()))
                .collect(Collectors.toList());
    }

    private User findUser(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
    }
}
