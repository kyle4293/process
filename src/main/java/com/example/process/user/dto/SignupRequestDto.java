package com.example.process.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class SignupRequestDto {
    @NotBlank
    private String username;


    private String nickname;

    @NotBlank
    private String password;

    private String introduction;

    @NotBlank
    private String email;

    private boolean admin = false;
    private String adminToken = "";
}