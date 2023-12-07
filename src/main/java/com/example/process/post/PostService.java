package com.example.process.post;


import com.example.process.entity.ErrorCode;
import com.example.process.exception.CustomException;
import com.example.process.user.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    //게시물 생성
    @Transactional
    public PostResponseDto createPost(User user, PostRequestDto requestDto) {

        Post post = new Post(requestDto);
        post.User(user);

        postRepository.save(post);
        return new PostResponseDto(post);
    }

    //게시물 전체조회 (생성일 기준 내림차순)
    @Transactional
    public List<PostResponseDto> getPostList() {
        return postRepository.findAllByOrderByCreatedAtDesc().stream().map(PostResponseDto::new).toList();
    }

    //게시물 단일 조회
    @Transactional
    public PostResponseDto getPost(Long id) {
        Post post = findPost(id);
        return new PostResponseDto(post);
    }

    //게시물 수정
    @Transactional
    public PostResponseDto updatePost(Long id, User user, PostRequestDto requestDto) {

        Post post = findPost(id);
        if (post.getUser().getId()==user.getId()){
            post.Update(requestDto);
        }
        else {
            throw new CustomException(ErrorCode.ACCESS_DENIED);
        }

        return new PostResponseDto(post);
    }

    @Transactional
    public Long deletePost(Long id, User user) {
        Post post = findUserPost(id, user);
        postRepository.delete(post);
        return id;
    }
    
    private Post findPost(Long id) {
        return postRepository.findById(id).orElseThrow(() -> {
                    throw new CustomException(ErrorCode.INDEX_NOT_FOUND);
                }
        );
    }

    public Post findUserPost(Long id, User user) {
        Post post = findPost(id);

        if (!user.getId().equals(post.getUser().getId())) {
            throw new CustomException(ErrorCode.ACCESS_DENIED);
        }
        return post;
    }
}