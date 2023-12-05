package com.example.process.comment;

import com.example.process.entity.ErrorCode;
import com.example.process.exception.CustomException;
import com.example.process.post.Post;
import com.example.process.post.PostRepository;
import com.example.process.user.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CommentService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public CommentService(PostRepository postRepository, CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }

    @Transactional
    public CommentResponseDto addComment(Long post_id, User user, CommentRequestDto requestDto) {
        Post post = findPost(post_id);
        Comment comment = new Comment(user, post, requestDto);
        Comment saveComment = commentRepository.save(comment);

        CommentResponseDto commentResponseDto = new CommentResponseDto(saveComment);

        return commentResponseDto;
    }

    @Transactional
    public List<CommentResponseDto> getComments(Long id) {
        Post post = findPost(id);

        List<Comment> comments = post.getComments();
        List<CommentResponseDto> commentResponseDtos = comments.stream().map(CommentResponseDto::new).toList();

        return commentResponseDtos;
    }

    @Transactional
    public CommentResponseDto updateComment(Long id, User user, CommentRequestDto requestDto) {
        Comment comment = findComment(id);
        if (comment.getUser().getId()==user.getId()) {
            comment.update(requestDto);
        } else {
            throw new CustomException(ErrorCode.ACCESS_DENIED);
        }

        CommentResponseDto commentResponseDto = new CommentResponseDto(comment);

        return commentResponseDto;
    }

    @Transactional
    public Long deleteComment(Long id, User user) {
        Comment comment = findComment(id);
        if (comment.getUser().getId()==user.getId()) {
            commentRepository.delete(comment);
        } else {
            throw new CustomException(ErrorCode.ACCESS_DENIED);
        }
        return id;
    }

    @Transactional
    private Post findPost(Long id) {
        return postRepository.findById(id).orElseThrow(() -> {
                    throw new CustomException(ErrorCode.INDEX_NOT_FOUND);
                }
        );
    }

    @Transactional
    private Comment findComment(Long id) {
        return commentRepository.findById(id).orElseThrow(() -> {
                    throw new CustomException(ErrorCode.INDEX_NOT_FOUND);
                }
        );
    }
}
