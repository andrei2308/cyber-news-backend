package com.example.demo.domain.newsLikes.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewsLikeDto {
    private String userId;
    private String username;
    private String email;

    public NewsLikeDto(String userId, String username, String email) {
        this.userId = userId;
        this.username = username;
        this.email = email;
    }
}