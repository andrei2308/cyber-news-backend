package com.example.demo.service.userFollow;

import com.example.demo.domain.user.dtos.UserProfile;
import com.example.demo.domain.user.entity.User;
import com.example.demo.domain.userFollow.UserFollow;
import com.example.demo.domain.userFollow.repository.UserFollowRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserFollowServiceImpl implements UserFollowService {

    private final ModelMapper modelMapper;
    private final UserFollowRepository userFollowRepository;

    public UserFollowServiceImpl(ModelMapper modelMapper, UserFollowRepository userFollowRepository) {
        this.modelMapper = modelMapper;
        this.userFollowRepository = userFollowRepository;
    }

    @Override
    public boolean checkAlreadyFollowing(String currentUserId, String followingUserId) {
        return userFollowRepository.existsByFollowerIdAndFollowingId(currentUserId, followingUserId);
    }

    @Override
    public boolean checkNotFollowing(String currentUserId, String followingUserId) {
        return !userFollowRepository.existsByFollowerIdAndFollowingId(currentUserId, followingUserId);
    }

    @Override
    public void followUser(User currentUser, User followingUser) {
        UserFollow userFollow = new UserFollow();
        userFollow.setFollower(currentUser);
        userFollow.setFollowing(followingUser);

        userFollowRepository.save(userFollow);
    }

    @Override
    public void unfollowUser(User currentUser, User unfollowingUser) {
        String idToDelete = userFollowRepository.findUserToUnfollow(currentUser.getId(), unfollowingUser.getId());
        userFollowRepository.deleteById(idToDelete);
    }

    @Override
    public List<UserProfile> getFollowers(String userId) {
        return userFollowRepository.findFollowersByUserId(userId)
                .stream()
                .map(user -> modelMapper.map(user, UserProfile.class))
                .toList();
    }

    @Override
    public List<UserProfile> getFollowing(String userId) {
        return userFollowRepository.findFollowingsByUserId(userId)
                .stream().map(user -> modelMapper.map(user, UserProfile.class))
                .toList();
    }
}
