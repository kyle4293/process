package com.example.process.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Column
    private String intro;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    public User(String username, String password, String email, String intro, UserRoleEnum role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.intro = intro;
        this.role = role;
    }

    public void Update(UserRequestDto requestDto) {
        this.intro = requestDto.getIntro();
        this.email = requestDto.getEmail();
    }
}