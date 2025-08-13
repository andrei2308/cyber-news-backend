package com.example.demo.presentation.news;

import com.example.demo.domain.constants.Constants;
import com.example.demo.domain.news.dtos.NewsDto;
import com.example.demo.service.news.NewsService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = NewsController.API_NEWS_ROUTE)
public class NewsController {
    public static final String API_NEWS_ROUTE = Constants.DEFAULT_ROUTE + "/news";
    private final NewsService newsService;
    private final ModelMapper modelMapper;

    public NewsController(NewsService newsService, ModelMapper modelMapper) {
        this.newsService = newsService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResponseEntity<List<NewsDto>> getNews() {
        return ResponseEntity.ok(newsService.getAllNews());
    }

    @GetMapping(value = "/{userId}")
    public ResponseEntity<List<NewsDto>> getUserNews(@PathVariable String userId) {
        return ResponseEntity.ok(newsService.getUserNews(userId));
    }
}
