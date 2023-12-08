package com.example.process.like;

import com.example.process.CommonResponseDto;
import com.example.process.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post")
public class PostLikeController {

    private final PostLikeService postLikeService;

    @PostMapping("/{postId}/like")
    public ResponseEntity<CommonResponseDto> like(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails) {

        postLikeService.like(postId, userDetails.getUser());

        return ResponseEntity.ok(new CommonResponseDto("좋아요를 누르셨습니다", HttpStatus.OK.value()));
    }

    @DeleteMapping("{postId}/like")
    public ResponseEntity<CommonResponseDto> dislike(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails) {

        postLikeService.unlike(postId, userDetails.getUser());

        return ResponseEntity.ok().body(new CommonResponseDto("좋아요가 취소되었습니다", HttpStatus.OK.value()));
    }
}