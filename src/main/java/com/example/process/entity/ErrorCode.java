package com.example.process.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    INDEX_NOT_FOUND(HttpStatus.NOT_FOUND, "인덱스가 존재하지 않습니다."),
    UNKNOWN_ERROR(HttpStatus.BAD_REQUEST, "토큰이 존재하지 않습니다."),
    WRONG_TYPE_TOKEN(HttpStatus.BAD_REQUEST, "변조된 토큰입니다."),
    EXPIRED_TOKEN(HttpStatus.BAD_REQUEST, "만료된 토큰입니다."),
    UNSUPPORTED_TOKEN(HttpStatus.BAD_REQUEST, "변조된 토큰입니다."),
    ACCESS_DENIED(HttpStatus.BAD_REQUEST, "권한이 없습니다.");

    private HttpStatus status;
    private String message;


}