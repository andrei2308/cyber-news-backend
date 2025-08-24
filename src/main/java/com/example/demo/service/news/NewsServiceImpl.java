package com.example.demo.service.news;

import com.example.demo.domain.news.dtos.NewsCreateVM;
import com.example.demo.domain.news.dtos.NewsDto;
import com.example.demo.domain.news.entity.News;
import com.example.demo.domain.news.repository.NewsRepository;
import com.example.demo.domain.user.entity.User;
import com.example.demo.domain.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class NewsServiceImpl implements NewsService {

    private final NewsRepository newsRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public NewsServiceImpl(NewsRepository newsRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.newsRepository = newsRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<NewsDto> getAllNews() {
        List<News> newsList = newsRepository.findAllWithUsers();
        return newsList.stream().map(news -> modelMapper.map(news, NewsDto.class)).toList();
    }

    public List<NewsDto> getUserNews(String userId) {
        List<News> userNews = newsRepository.findNewsByUserIdWithUser(userId);
        return userNews.stream()
                .map(news -> modelMapper.map(news, NewsDto.class))
                .toList();
    }

    @Override
    @Transactional
    public NewsDto createNewsForUser(String userId, NewsCreateVM newsCreateVM) {
        try {
            User userReference = userRepository.getReferenceById(userId);

            News news = modelMapper.map(newsCreateVM, News.class);

            news.setUser(userReference);
            news.setCreatedDate(new Date());
            news.setCreatedBy(userReference.getUsername());

            return modelMapper.map(newsRepository.save(news), NewsDto.class);
        } catch (EntityNotFoundException e) {
            return null;
        }
    }
}
