package com.example.process.comment;

import com.example.process.post.PostService;
import com.example.process.security.UserDetailsImpl;
import com.example.process.user.User;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/post/{id}/comments")
    public CommentResponseDto addComment(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long id, @Valid @RequestBody CommentRequestDto requestDto) {
        User user = userDetails.getUser();
        return commentService.addComment(id, user, requestDto);
    }

    @GetMapping("/post/{id}/comments")
    public List<CommentResponseDto> getComments(@PathVariable Long id) {
        return commentService.getComments(id);
    }

    @PutMapping("/post/comments/{id}")
    public CommentResponseDto updateComment(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long id, @RequestBody CommentRequestDto requestDto) {
        User user = userDetails.getUser();
        return commentService.updateComment(id, user, requestDto);
    }

    @DeleteMapping("/post/comments/{id}")
    public Long deleteComments(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long id) {
        User user = userDetails.getUser();

        return commentService.deleteComment(id, user);
    }

}
