package com.example.demo.service.news;

import com.example.demo.domain.news.dtos.NewsCreateVM;
import com.example.demo.domain.news.dtos.NewsDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface NewsService {
    List<NewsDto> getAllNews();

    List<NewsDto> getUserNews(String userId);

    NewsDto createNewsForUser(String userId, NewsCreateVM newsCreateVM);
}
