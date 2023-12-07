package com.example.process.user.entity;

import com.example.process.entity.Timestamped;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20, unique = true)
    private String username;

    @Column(length = 10)
    private String nickname;

    @Column(nullable = false, length=20)
    private String password;

    @Column(length = 50)
    private String introduction;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    private Long kakaoId;

    public User(
            String username, String nickname,
            String password, String introduction,
            String email, UserRoleEnum role
    ) {
        this.username = username;
        this.nickname = nickname;
        this.password = password;
        this.introduction = introduction;
        this.email = email;
        this.role = role;
    }

    public User(
            String username, String password,
            String email, UserRoleEnum role, Long kakaoId
    ) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.kakaoId =kakaoId;
    }

    public User kakaoIdUpdate(Long kakaoId) {
        this.kakaoId = kakaoId;
        return this;
    }
}