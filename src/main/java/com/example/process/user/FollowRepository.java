package com.example.process.user;

import com.example.process.user.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    Optional<Follow> findByFollowerIdAndFollowingId(Long followingId, Long followerId);

    Follow getFollowerByFollowerId(Long id);
}
