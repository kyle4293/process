package com.example.process.user.repository;

import com.example.process.user.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    Optional<Follow> findByFollowerIdAndFollowingId(Long followingId, Long followerId);

    Follow getFollowerByFollowerId(Long id);
}
