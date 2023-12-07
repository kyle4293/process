package com.example.process.post;

import com.example.process.security.UserDetailsImpl;
import com.example.process.user.User;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/process")
public class PostController {

    private final PostService postService;

    //@RequiredArgsConstructor를 사용하는거보다 직접 생성자 주입을 하는 것을 권장
    public PostController(PostService postService) {
        this.postService = postService;
    }

    //게시물 단일조회
    @GetMapping("/post/{id}")
    public PostResponseDto getPost(@PathVariable Long id) {

        return postService.getPost(id);
    }

    //게시물 전체 조회
    @GetMapping("/posts")
    public List<PostResponseDto> getPostList() {

        return postService.getPostList();
    }

    //게시물 생성
    @PostMapping("/post")
    public PostResponseDto createPost(@AuthenticationPrincipal UserDetailsImpl userDetails, @Valid @RequestBody PostRequestDto requestDto) {

        User user = userDetails.getUser();
        return postService.createPost(user, requestDto);
    }

    //게시물 수정
    @PutMapping("/post/{id}")
    public PostResponseDto updatePost(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long id, @RequestBody PostRequestDto requestDto) {

        User user = userDetails.getUser();
        return postService.updatePost(id, user, requestDto);
    }

    //게시물 삭제
    @DeleteMapping("/post/{id}")
    public Long deletePost(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long id) {

        User user = userDetails.getUser();

        return postService.deletePost(id, user);
    }

}