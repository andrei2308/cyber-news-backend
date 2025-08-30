package com.example.demo.domain.userFollow.repository;

import com.example.demo.domain.userFollow.entity.UserFollow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserFollowRepository extends JpaRepository<UserFollow, String> {
    boolean existsByFollowerIdAndFollowingId(String followerId, String followingId);

    @Query("SELECT uf.id FROM UserFollow uf WHERE uf.follower.id=:currentUserId AND uf.following.id=:unfollowingUserId")
    String findUserToUnfollow(@Param("currentUserId") String currentUserId, @Param("unfollowingUserId") String unfollowingUserId);
}
