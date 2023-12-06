package com.example.process.like;

import com.example.process.entity.ErrorCode;
import com.example.process.exception.CustomException;
import com.example.process.post.Post;
import com.example.process.post.PostRepository;
import com.example.process.user.User;
import com.example.process.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostLikeService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;
    @Transactional
    public void like(Long postId, User user) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new CustomException(ErrorCode.POST_NOT_FOUND)
        );

        User loginUser = userRepository.findById(user.getId()).orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_FOUND)
        );

        if (post.getUser().getId().equals(loginUser.getId())) {
            throw new CustomException(ErrorCode.SELF_LIKE_ERROR);
        }

        PostLike postLike = PostLike.builder()
                .post(post)
                .user(loginUser)
                .build();

        if(postLikeRepository.findByPostAndUser(post, loginUser).isPresent()){
            throw new IllegalArgumentException("이미 좋아요 누름");
        }

        postLikeRepository.save(postLike);

    }
    @Transactional
    public void unlike(Long postId, User user) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new CustomException(ErrorCode.POST_NOT_FOUND)
        );

        User loginUser = userRepository.findById(user.getId()).orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_FOUND)
        );

        Optional<PostLike> findPostLike = postLikeRepository.findByPostAndUser(post, loginUser);
        if (findPostLike.isEmpty()) {
            throw new IllegalArgumentException("좋아요 안누름");
        }

        PostLike postLike = findPostLike.get();
        postLikeRepository.delete(postLike);
    }
}
