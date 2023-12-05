package com.example.process.post;


import com.example.process.security.UserDetailsImpl;
import com.example.process.user.entity.User;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/post")
    public PostResponseDto createPost(@AuthenticationPrincipal UserDetailsImpl userDetails, @Valid @RequestBody PostRequestDto requestDto) {
        User user = userDetails.getUser();
        System.out.println("user.getUsername() = " + user.getUsername());
        System.out.println("user.getEmail() = " + user.getEmail());

        return postService.createPost(user, requestDto);
    }


    @GetMapping("/post")
    public List<PostResponseDto> getPostList() {
        return postService.getPostList();
    }

    @GetMapping("/post/{id}")
    public PostResponseDto getPost(@PathVariable Long id) {
        return postService.getPost(id);
    }


    @PutMapping("/post/{id}")
    public PostResponseDto updatePost(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long id, @RequestBody PostRequestDto requestDto) {
        User user = userDetails.getUser();
        return postService.updatePost(id, user, requestDto);
    }

    @DeleteMapping("/post/{id}")
    public Long deletePost(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long id) {
        User user = userDetails.getUser();

        return postService.deletePost(id, user);
    }

}