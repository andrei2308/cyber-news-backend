package com.example.demo.service.userFollow;

import com.example.demo.domain.user.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface UserFollowService {
    boolean checkAlreadyFollowing(String currentUserId, String followingUserId);

    void followUser(User currentUser, User followingUser);

    void unfollowUser(User currentUser, User unfollowingUser);
}
