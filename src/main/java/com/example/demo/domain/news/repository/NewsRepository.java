package com.example.demo.domain.news.repository;

import com.example.demo.domain.news.entity.News;
import com.example.demo.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface NewsRepository extends JpaRepository<News, String> {
    Set<News> findNewsByUser(User user);

    @Query("SELECT n FROM News n JOIN FETCH n.user WHERE n.user.id = :userId")
    List<News> findNewsByUserIdWithUser(@Param("userId") String userId);

    @Query("SELECT n FROM News n JOIN FETCH n.user")
    List<News> findAllWithUsers();
}
