package com.example.demo.domain.news.repository;

import com.example.demo.domain.news.entity.News;
import com.example.demo.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface NewsRepository extends JpaRepository<News, String> {
    Set<News> findNewsByUser(User user);
}
