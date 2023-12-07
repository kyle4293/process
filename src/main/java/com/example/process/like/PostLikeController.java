package com.example.process.like;

import com.example.process.user.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/process/post")
public class PostLikeController {

    private final PostLikeService postLikeService;

    @PostMapping("/{post_id}/like")
    public ResponseEntity<String> like(@PathVariable Long post_id, @AuthenticationPrincipal UserDetailsImpl userDetails) {

        postLikeService.like(post_id, userDetails.getUser());

        return ResponseEntity.ok("좋아요");
    }

    @DeleteMapping("{post_id}/like")
    public ResponseEntity<String> dislike(@PathVariable Long post_id, @AuthenticationPrincipal UserDetailsImpl userDetails) {

        postLikeService.unlike(post_id, userDetails.getUser());

        return ResponseEntity.ok("좋아요 취소");
    }
}
