package com.example.process.user.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;

@Getter
public class SignupRequestDto {
    @Pattern(regexp = "^[a-z0-9]{4,20}$",
            message = "username은 a ~ z, 0 ~ 9 만 포함, 4이상 20이하")
    private String username;

    @Pattern(regexp = "^[a-zA-Z0-9가-힣]{2,10}$",
            message = "닉네임은 a ~ z, A ~ Z, 0 ~ 9, 한글 만 포함, 2이상 20이하")
    private String nickname;


    @Pattern(regexp = "^[a-z0-9]{8,20}$",
            message = "비밀번호는 a ~ z, 0 ~ 9만 포함, 8이상 20이하")
    private String password;

    @Size(max = 40, message = "한줄소개는 최대 50자입니다.")
    private String introduction;

    @Email
    private String email;

    private boolean admin = false;
    private String adminToken = "";
}