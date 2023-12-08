package com.example.process.post;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class PostRequestDto {

    @NotBlank //null과 "" " " 전부 허용X
    private String title;

    @NotBlank //null과 "" " " 전부 허용X
    private String contents;
}
