package com.example.process.comment;

import com.example.process.CommonResponseDto;
import com.example.process.security.UserDetailsImpl;
import com.example.process.user.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/post/{postId}/comment")
    public ResponseEntity<CommentResponseDto> addComment(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long postId, @Valid @RequestBody CommentRequestDto requestDto) {
        User user = userDetails.getUser();

        return ResponseEntity.status(HttpStatus.CREATED.value()).body(commentService.addComment(postId, user,requestDto));

    }

    @GetMapping("/post/{postId}/comment")
    public ResponseEntity<List<CommentResponseDto>> getComments(@PathVariable Long postId) {

        return ResponseEntity.ok(commentService.getComments(postId));
    }

    @PutMapping("/post/{postId}/comment/{commentId}")
    public ResponseEntity<CommentResponseDto> updateComment(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long postId, @PathVariable Long commentId, @RequestBody CommentRequestDto requestDto) {
        User user = userDetails.getUser();

        return ResponseEntity.ok(commentService.updateComment(postId, commentId , user, requestDto));
    }

    @DeleteMapping("/post/{postId}/comment/{commentId}")
    public ResponseEntity<CommonResponseDto> deleteComments(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long postId, @PathVariable Long commentId) {
        User user = userDetails.getUser();
        commentService.deleteComment(postId, commentId, user);

        return ResponseEntity.ok(new CommonResponseDto("삭제가 완료되었습니다", HttpStatus.OK.value()));
    }
}