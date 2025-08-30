package com.example.demo.domain.newsLikes.repository;

import com.example.demo.domain.newsLikes.entity.NewsLikes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserLikeRepository extends JpaRepository<NewsLikes, String> {

    @Query("SELECT nl.id FROM NewsLikes nl WHERE nl.likedUser.id=:currentUserId AND nl.likedPost.id=:newsToUnlikeId")
    String findLikedNewsByNewsIdAndUserId(@Param("currentUserId") String currentUserId, @Param("newsToUnlikeId") String newsToUnlikeId);
}
