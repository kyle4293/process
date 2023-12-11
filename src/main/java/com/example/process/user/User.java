package com.example.process.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


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

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    private Long kakaoId;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "following")
    private List<Follow> followers = new ArrayList<>();

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "follower")
    private List<Follow> followings = new ArrayList<>();

    private String intro;

    public User(String username, String password, String email, UserRoleEnum role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    public User(String username, String password, String email, UserRoleEnum role, Long kakaoId) {
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

    public void addFollowing(Follow follow) {
        followings.add(follow);
        follow.setFollowing(this);
    }

    public void addFollower(Follow follow) {
        followers.add(follow);
        follow.setFollower(this);
    }

    public void removeFollowing(Follow follow) {
        this.followings.remove(follow);
        follow.setFollowing(null);
    }

    public void removeFollower(Follow follow) {
        this.followers.remove(follow);
        follow.setFollower(null);
    }

    public void updateProfile(ProfileRequestDto requestDto){
        this.intro = requestDto.getIntro();
        this.email = requestDto.getEmail();
        this.password = requestDto.getPassword();
    }
}