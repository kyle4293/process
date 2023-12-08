package com.example.process.user.entity;

import com.example.process.entity.Timestamped;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


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

    @Column(nullable = false, length = 60)
    private String password;

    @Column(length = 50)
    private String introduction;

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
        this.kakaoId = kakaoId;
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
}