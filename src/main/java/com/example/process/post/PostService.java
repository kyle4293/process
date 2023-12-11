package com.example.process.post;


import com.example.process.comment.CommentRepository;
import com.example.process.entity.ErrorCode;
import com.example.process.exception.CustomException;
import com.example.process.user.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public PostService(PostRepository postRepository, CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }

    @Transactional
    public PostResponseDto createPost(User user, PostRequestDto requestDto) {
        // RequestDto -> Entity
        Post post = new Post(user, requestDto);

        // DB 저장
        Post savePost = postRepository.save(post);

        // Entity -> ResponseDto
        PostResponseDto postResponseDto = new PostResponseDto(savePost);

        return postResponseDto;
    }


    @Transactional
    public List<PostResponseDto> getPostList() {
        // DB 조회
        return postRepository.findAllByOrderByModifiedAtDesc().stream().map(PostResponseDto::new).toList();
    }

    @Transactional
    public PostResponseDto getPost(Long id) {
        Post post = findPost(id);
        PostResponseDto postResponseDto = new PostResponseDto(post);
        return postResponseDto;
    }


    @Transactional
    public PostResponseDto updatePost(Long id, User user, PostRequestDto requestDto) {
        // 해당 메모가 DB에 존재하는지 확인
        Post post = findPost(id);
        if (post.getUser().getId()==user.getId()){
            // post 내용 수정
            post.update(requestDto);
        }
        else {
            throw new CustomException(ErrorCode.ACCESS_DENIED);
        }

        PostResponseDto postResponseDto = new PostResponseDto(post);
        return postResponseDto;
    }


    @Transactional
    public Long deletePost(Long id, User user) {
        // 해당 메모가 DB에 존재하는지 확인
        Post post = findPost(id);
        if (post.getUser().getId()==user.getId()){
            // post 삭제
            postRepository.delete(post);
        } else {
            throw new CustomException(ErrorCode.ACCESS_DENIED);
        }

        return id;
    }


    @Transactional
    public Post findPost(Long id) {
        return postRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.INDEX_NOT_FOUND)
        );
    }
}