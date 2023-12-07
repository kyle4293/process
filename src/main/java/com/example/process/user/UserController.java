package com.example.process.user;


import com.example.process.post.PostRequestDto;
import com.example.process.post.PostResponseDto;
import com.example.process.security.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/process")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    //회원가입
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@Valid @RequestBody SignupRequestDto requestDto, BindingResult bindingResult) {
        // Validation 예외처리
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        if(fieldErrors.size() > 0) {
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                log.error(fieldError.getField() + " 필드 : " + fieldError.getDefaultMessage());
            }
            return new ResponseEntity<>("회원가입 실패", HttpStatusCode.valueOf(400));
        }

        userService.signup(requestDto);

        return new ResponseEntity<>("회원가입 성공", HttpStatus.CREATED);
    }

    //프로필 조회
    @GetMapping("/profile/{username}")
    public UserResponseDto getProfile(@PathVariable String username) {
        return userService.getProfile(username);
    }

    //프로필 수정
    @PutMapping("/profile/{username}")
    public UserResponseDto updateProfile(@PathVariable String username, @RequestBody UserRequestDto requestDto) {

        return userService.updateProfile(username, requestDto);
    }

    //비밀번호 수정
    @PutMapping("/password/{username}")
    public ResponseEntity<String> updatePassword(@RequestBody PasswordUpdateRequestDto passwordUpdateRequest, @PathVariable String username) {

            String currentPassword = passwordUpdateRequest.getCurrentPassword();
            String newPassword = passwordUpdateRequest.getNewPassword();
            return userService.updatePassword(username, currentPassword, newPassword);
    }

}