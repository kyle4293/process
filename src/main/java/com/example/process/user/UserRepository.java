package com.example.process.user;

import com.example.process.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {     //spring data jpa
    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    Optional<User> findByKakaoId(Long kakaoId);

    List<User> findAll();

}
