package com.example.process.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordUpdateRequestDto {
    private String currentPassword;
    private String newPassword;
}