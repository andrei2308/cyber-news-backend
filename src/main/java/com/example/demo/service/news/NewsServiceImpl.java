package com.example.demo.service.news;

import com.example.demo.domain.news.dtos.NewsCreateVM;
import com.example.demo.domain.news.dtos.NewsDto;
import com.example.demo.domain.news.entity.News;
import com.example.demo.domain.news.repository.NewsRepository;
import com.example.demo.domain.user.entity.User;
import com.example.demo.domain.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
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
    public NewsDto createNewsForUser(String userId, NewsCreateVM newsCreateVM) {
        try {
            User userReference = userRepository.getReferenceById(userId);

            News news = new News();
            news.setUser(userReference);
            news.setCreatedDate(new Date());
            news.setDescription(newsCreateVM.description);
            news.setCreatedBy(userReference.getUsername());
            news.setTitle(newsCreateVM.title);

            return modelMapper.map(newsRepository.save(news), NewsDto.class);
        } catch (EntityNotFoundException e) {
            return null;
        }
    }
}
