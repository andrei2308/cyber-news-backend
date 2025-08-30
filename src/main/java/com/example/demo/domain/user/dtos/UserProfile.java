package com.example.demo.domain.user.dtos;

import com.example.demo.domain.userFollow.dtos.UserFollowDto;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class UserProfile {
    private String username;
    private String email;
    private String id;
    private Set<UserFollowDto> followers;
    private Set<UserFollowDto> following;
}
