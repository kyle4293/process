package com.example.process.like;

import com.example.process.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/process/comment")
public class CommentLikeController {

    private final CommentLikeService commentLikeService;

    @PostMapping("/{comment_id}/like")
    public ResponseEntity<String> like(@PathVariable Long comment_id, @AuthenticationPrincipal UserDetailsImpl userDetails) {

        commentLikeService.like(comment_id, userDetails.getUser());

        return ResponseEntity.ok("좋아요");
    }

    @DeleteMapping("{comment_id}/like")
    public ResponseEntity<String> dislike(@PathVariable Long comment_id, @AuthenticationPrincipal UserDetailsImpl userDetails) {

        commentLikeService.unlike(comment_id, userDetails.getUser());

        return ResponseEntity.ok("좋아요 취소");
    }
}
