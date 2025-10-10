package com.example.demo.domain.news.repository;

import com.example.demo.domain.news.entity.News;
import com.example.demo.domain.user.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface NewsRepository extends JpaRepository<News, String> {

    @EntityGraph("News.withUserAndLikes")
    List<News> findByUserId(@Param("userId") String userId);

    @EntityGraph("News.withUserAndLikes")
    Optional<News> findById(String id);

    @EntityGraph("News.withUserAndLikes")
    List<News> findByUserIdNot(String id);
}
