package com.example.demo.domain.userFollow.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserFollowDto {
    private String id;
    private String username;
    private String email;

    public UserFollowDto() {
    }

    public UserFollowDto(String id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
    }
}
