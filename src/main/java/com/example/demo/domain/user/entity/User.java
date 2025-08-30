package com.example.demo.domain.user.entity;

import com.example.demo.domain.InformationEntity;
import com.example.demo.domain.news.entity.News;
import com.example.demo.domain.newsLikes.entity.NewsLikes;
import com.example.demo.domain.userFollow.entity.UserFollow;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity(name = "users")
@Setter
@Getter
public class User extends InformationEntity {

    @Column(name = "UserRole")
    private String userRole;

    @Column(name = "Email")
    private String email;

    @Column(name = "Username", nullable = false, unique = true)
    private String username;

    @Column(name = "Password", nullable = false)
    private String password;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonIgnore
    private Set<News> news = new HashSet<>();

    @OneToMany(mappedBy = "follower", fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonIgnore
    private Set<UserFollow> following = new HashSet<>();

    @OneToMany(mappedBy = "following", fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonIgnore
    private Set<UserFollow> followers = new HashSet<>();

    @OneToMany(mappedBy = "likedUser", fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonIgnore
    private Set<NewsLikes> likedNews = new HashSet<>();
}
