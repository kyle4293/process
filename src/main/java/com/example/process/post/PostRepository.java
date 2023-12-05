package com.example.process.post;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    // Query Methods: 메서드 이름으로 SQL을 생성(Select * ~)
    List<Post> findAllByOrderByModifiedAtDesc();
}