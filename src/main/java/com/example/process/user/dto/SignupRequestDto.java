package com.example.process.user.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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