package com.example.process.comment;

import com.example.process.entity.ErrorCode;
import com.example.process.exception.CustomException;
import com.example.process.post.Post;
import com.example.process.post.PostRepository;
import com.example.process.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public CommentResponseDto addComment(Long postId, User user, CommentRequestDto requestDto) {
        Post post = findPost(postId);

        Comment comment = new Comment(requestDto);

        comment.setUser(user);
        comment.setPost(post);

        Comment saveComment = commentRepository.save(comment);

        return new CommentResponseDto(saveComment);
    }

    public List<CommentResponseDto> getComments(Long postId) {
        Post post = findPost(postId);

        List<Comment> comments = post.getComments();

        return comments.stream().map(CommentResponseDto::new).toList();
    }

    @Transactional
    public CommentResponseDto updateComment(Long postId, Long commentId,  User user, CommentRequestDto requestDto) {
        Post post = findPost(postId);

        Comment comment = findComment(commentId);

        if (comment.getUser().getId().equals(user.getId())) {
            comment.update(requestDto);
        } else {
            throw new CustomException(ErrorCode.ACCESS_DENIED);
        }

        return new CommentResponseDto(comment);
    }

    public void deleteComment(Long postId, Long commentId, User user) {
        Post post = findPost(postId);

        Comment comment = findComment(commentId);

        if (comment.getUser().getId().equals(user.getId())) {
            commentRepository.delete(comment);
        } else {
            throw new CustomException(ErrorCode.ACCESS_DENIED);
        }
    }


    public Post findPost(Long id) {
        return postRepository.findById(id).orElseThrow(
                () -> new CustomException(ErrorCode.INDEX_NOT_FOUND)
                );
    }

    public Comment findComment(Long id) {
        return commentRepository.findById(id).orElseThrow(
                () -> new CustomException(ErrorCode.INDEX_NOT_FOUND)
        );
    }
}
