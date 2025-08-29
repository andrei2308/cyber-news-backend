package com.example.demo.service.userFollow;

import com.example.demo.domain.user.dtos.UserProfile;
import com.example.demo.domain.user.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserFollowService {
    boolean checkAlreadyFollowing(String currentUserId, String followingUserId);

    boolean checkNotFollowing(String currentUserId, String followingUserId);

    void followUser(User currentUser, User followingUser);

    void unfollowUser(User currentUser, User unfollowingUser);

    List<UserProfile> getFollowers(String userId);

    List<UserProfile> getFollowing(String userId);
}
