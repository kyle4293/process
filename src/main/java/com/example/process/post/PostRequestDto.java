package com.example.process.post;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
public class PostRequestDto {
    @NotBlank
    private String title;
    @NotBlank
    private String contents;
}
