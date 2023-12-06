package com.example.process.like;

import com.example.process.entity.ErrorCode;
import com.example.process.exception.CustomException;
import com.example.process.comment.Comment;
import com.example.process.comment.CommentRepository;
import com.example.process.user.User;
import com.example.process.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentLikeService {

    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final CommentLikeRepository commentLikeRepository;
    @Transactional
    public void like(Long commentId, User user) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new CustomException(ErrorCode.COMMENT_NOT_FOUND)
        );

        User loginUser = userRepository.findById(user.getId()).orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_FOUND)
        );

        if (comment.getUser().getId().equals(loginUser.getId())) {
            throw new CustomException(ErrorCode.SELF_LIKE_ERROR);
        }

        CommentLike commentLike = CommentLike.builder()
                .comment(comment)
                .user(loginUser)
                .build();

        if(commentLikeRepository.findByCommentAndUser(comment, loginUser).isPresent()){
            throw new CustomException(ErrorCode.ALREADY_EXIST_LIKE);
        }

        commentLikeRepository.save(commentLike);

    }
    @Transactional
    public void unlike(Long commentId, User user) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new CustomException(ErrorCode.COMMENT_NOT_FOUND)
        );

        User loginUser = userRepository.findById(user.getId()).orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_FOUND)
        );

        Optional<CommentLike> findCommentLike = commentLikeRepository.findByCommentAndUser(comment, loginUser);
        if (findCommentLike.isEmpty()) {
            throw new CustomException(ErrorCode.NOT_FOUND_LIKE);
        }

        CommentLike commentLike = findCommentLike.get();
        commentLikeRepository.delete(commentLike);
    }
}
