package com.example.demo.presentation.news;

import com.example.demo.domain.constants.Constants;
import com.example.demo.domain.news.dtos.CveDetailsResponse;
import com.example.demo.domain.news.dtos.NewsDto;
import com.example.demo.service.news.NewsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = NewsController.API_NEWS_ROUTE)
public class NewsController {
    public static final String API_NEWS_ROUTE = Constants.DEFAULT_ROUTE + "/news";
    private final NewsService newsService;

    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @GetMapping
    public ResponseEntity<List<NewsDto>> getNews() {
        return ResponseEntity.ok(newsService.getAllNews());
    }

    @GetMapping(value = "/{userId}")
    public ResponseEntity<List<NewsDto>> getUserNews(@PathVariable String userId) {
        return ResponseEntity.ok(newsService.getUserNews(userId));
    }

    @GetMapping(value = "/details/{cveId}")
    public ResponseEntity<CveDetailsResponse> getNewsDetails(@PathVariable String cveId) {
        CveDetailsResponse cveDetailsResponse = newsService.fetchFromNIST(cveId);

        return ResponseEntity.ok(cveDetailsResponse);
    }

    @PostMapping(value = "/{newsId}/like")
    public ResponseEntity<String> like(@PathVariable String newsId) {
        newsService.likeNews(newsId);
        return ResponseEntity.ok("Successfully liked !");
    }

    @PostMapping(value = "{newsId}/unlike")
    public ResponseEntity<String> unlike(@PathVariable String newsId) {
        newsService.unlikeNews(newsId);
        return ResponseEntity.ok("Successfully unliked !");
    }
}
