package com.example.process.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class SignupRequestDto {
    @NotBlank
    private String username;

    @NotBlank
    private String nickname;

    @NotBlank
    private String password;

    @NotBlank
    private String introduction;

    @NotBlank
    private String email;

    private boolean admin = false;
    private String adminToken = "";
}