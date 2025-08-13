package com.example.demo.service.news;

import com.example.demo.domain.news.dtos.NewsDto;
import com.example.demo.domain.news.entity.News;
import com.example.demo.domain.news.repository.NewsRepository;
import com.example.demo.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsServiceImpl implements NewsService {

    private final NewsRepository newsRepository;
    private final UserRepository userRepository;

    public NewsServiceImpl(NewsRepository newsRepository, UserRepository userRepository) {
        this.newsRepository = newsRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<NewsDto> getAllNews() {
        List<News> newsList = newsRepository.findAll();

        return newsList.stream().map(this::mapNewsToNewsDto).toList();
    }

    @Override
    public List<NewsDto> getUserNews(String userId) {
        return userRepository.findById(userId)
                .map(newsRepository::findNewsByUser)
                .map(newsSet -> newsSet.stream()
                        .map(this::mapNewsToNewsDto)
                        .toList())
                .orElse(List.of());
    }

    private NewsDto mapNewsToNewsDto(News news) {
        NewsDto newsDto = new NewsDto();
        newsDto.description = news.getDescription();
        newsDto.createdAt = news.getCreatedDate();

        if (news.getUser() != null) {
            newsDto.authorUsername = news.getUser().getUsername();
            newsDto.userId = news.getUser().getId();
        }

        return newsDto;
    }
}
