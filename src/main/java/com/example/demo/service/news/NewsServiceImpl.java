package com.example.demo.service.news;

import com.example.demo.domain.news.dtos.NewsCreateVM;
import com.example.demo.domain.news.dtos.NewsDto;
import com.example.demo.domain.news.entity.News;
import com.example.demo.domain.news.repository.NewsRepository;
import com.example.demo.domain.user.entity.User;
import com.example.demo.domain.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Date;
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
        List<News> newsList = newsRepository.findAllWithUsers();
        return newsList.stream().map(this::mapNewsToNewsDto).toList();
    }

    public List<NewsDto> getUserNews(String userId) {
        List<News> userNews = newsRepository.findNewsByUserIdWithUser(userId);
        return userNews.stream()
                .map(this::mapNewsToNewsDto)
                .toList();
    }

    @Override
    public NewsDto createNewsForUser(String userId, NewsCreateVM newsCreateVM) {
        try {
            User userReference = userRepository.getReferenceById(userId);

            News news = new News();
            news.setUser(userReference);
            news.setCreatedDate(new Date());
            news.setDescription(newsCreateVM.description);
            news.setCreatedBy(userReference.getUsername());
            news.setTitle(newsCreateVM.title);

            return mapNewsToNewsDto(newsRepository.save(news));
        } catch (EntityNotFoundException e) {
            return null;
        }
    }

    private NewsDto mapNewsToNewsDto(News news) {
        NewsDto newsDto = new NewsDto();
        newsDto.description = news.getDescription();
        newsDto.createdAt = news.getCreatedDate();
        newsDto.title = news.getTitle();
        newsDto.severity = news.getSeverity();

        if (news.getUser() != null) {
            newsDto.authorUsername = news.getUser().getUsername();
            newsDto.userId = news.getUser().getId();
        }

        return newsDto;
    }
}
