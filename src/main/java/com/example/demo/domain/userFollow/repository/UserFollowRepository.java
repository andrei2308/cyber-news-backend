package com.example.demo.domain.userFollow.repository;

import com.example.demo.domain.user.entity.User;
import com.example.demo.domain.userFollow.entity.UserFollow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserFollowRepository extends JpaRepository<UserFollow, String> {
    boolean existsByFollowerIdAndFollowingId(String followerId, String followingId);

    @Query("SELECT uf.follower FROM UserFollow uf WHERE uf.following.id = :userId ORDER BY uf.createdDate DESC")
    List<User> findFollowersByUserId(@Param("userId") String userId);

    @Query("SELECT uf.following FROM UserFollow uf WHERE uf.follower.id=:userId order by uf.createdDate")
    List<User> findFollowingsByUserId(@Param("userId") String followingId);

    @Query("SELECT uf.id FROM UserFollow uf WHERE uf.follower.id=:currentUserId AND uf.following.id=:unfollowingUserId")
    String findUserToUnfollow(@Param("currentUserId") String currentUserId, @Param("unfollowingUserId") String unfollowingUserId);
}
