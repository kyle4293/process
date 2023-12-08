package com.example.process.like;

import com.example.process.CommonResponseDto;
import com.example.process.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/process")
public class CommentLikeController {

    private final CommentLikeService commentLikeService;

    @PostMapping("/posts/{postId}/comments/{commentId}/likes")
    public ResponseEntity<CommonResponseDto> like(@PathVariable Long postId, @PathVariable Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails) {

        commentLikeService.like(postId, commentId, userDetails.getUser());

        return ResponseEntity.ok(new CommonResponseDto("좋아요를 누르셨습니다", HttpStatus.OK.value()));
    }

    @DeleteMapping("/posts/{postId}/comments/{commentId}/likes")
    public ResponseEntity<CommonResponseDto> dislike(@PathVariable Long postId, @PathVariable Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails) {

        commentLikeService.unlike(postId, commentId, userDetails.getUser());

        return ResponseEntity.ok().body(new CommonResponseDto("좋아요가 취소되었습니다", HttpStatus.OK.value()));
    }
}
