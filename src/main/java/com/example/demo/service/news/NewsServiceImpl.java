package com.example.demo.service.news;

import com.example.demo.domain.enums.Severity;
import com.example.demo.domain.news.dtos.CveData;
import com.example.demo.domain.news.dtos.CveDetailsResponse;
import com.example.demo.domain.news.dtos.NewsCreateVM;
import com.example.demo.domain.news.dtos.NewsDto;
import com.example.demo.domain.news.dtos.nistMappingDtos.CveMinimal;
import com.example.demo.domain.news.dtos.nistMappingDtos.NistApiResponse;
import com.example.demo.domain.news.entity.News;
import com.example.demo.domain.news.repository.NewsRepository;
import com.example.demo.domain.user.entity.User;
import com.example.demo.domain.user.repository.UserRepository;
import com.example.demo.service.userLike.NewsLikeService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.List;

@Service
public class NewsServiceImpl implements NewsService {


    private final NewsLikeService newsLikeService;
    private final RestTemplate restTemplate;
    private final NewsRepository newsRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    @Value("${cve.nist.url}")
    private String nistUrl;


    public NewsServiceImpl(NewsRepository newsRepository, UserRepository userRepository, ModelMapper modelMapper, RestTemplate restTemplate, NewsLikeService userLikeService) {
        this.newsRepository = newsRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.restTemplate = restTemplate;
        this.newsLikeService = userLikeService;
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

    @Override
    public CveDetailsResponse fetchFromNIST(String cveId) {

        NistApiResponse nistApiResponse = restTemplate.getForObject(nistUrl + cveId, NistApiResponse.class);

        if (nistApiResponse != null && nistApiResponse.getResultsPerPage() == 0) {
            return new CveDetailsResponse(false, null, "CVE not found");
        }

        CveMinimal cve = nistApiResponse.getVulnerabilities().get(0).getCve();
        CveData cveData = extractCveData(cve);

        return new CveDetailsResponse(true, cveData, "");
    }

    @Override
    @Transactional
    public void likeNews(String newsId) {
        User currentUser = getCurrentUser();
        News newsToLike = newsRepository.findById(newsId).orElseThrow(() -> new IllegalArgumentException("News not found !"));

        checkNotAlreadyLiked(currentUser, newsToLike);

        newsLikeService.like(currentUser, newsToLike);
    }

    @Override
    @Transactional
    public void unlikeNews(String newsId) {
        User currentUser = getCurrentUser();
        News newsToUnlike = newsRepository.findById(newsId).orElseThrow(() -> new IllegalArgumentException("News not found !"));

        checkIsLiked(currentUser, newsToUnlike);

        newsLikeService.unlike(currentUser, newsToUnlike);
    }

    private CveData extractCveData(CveMinimal cve) {

        String affectedSystems;
        Double score;
        Severity severity;

        if (cve.getMetrics().getCvssMetricV31() != null) {
            score = cve.getMetrics()
                    .getCvssMetricV31()
                    .get(0).getCvssData()
                    .getBaseScore();

            severity = Severity.valueOf(
                    cve.getMetrics()
                            .getCvssMetricV31()
                            .get(0).
                            getCvssData()
                            .getBaseSeverity()
            );

            affectedSystems = "Not applicable";

            if (cve.getConfigurations() != null) {

                affectedSystems = cve
                        .getConfigurations()
                        .get(0)
                        .getNodes()
                        .get(0)
                        .getCpeMatch()
                        .get(0)
                        .getCriteria();
            }
        } else {
            score = cve.getMetrics()
                    .getCvssMetricV2()
                    .get(0).getCvssData()
                    .getBaseScore();

            severity = Severity.valueOf(
                    cve.getMetrics()
                            .getCvssMetricV2()
                            .get(0).
                            getCvssData()
                            .getBaseSeverity()
            );

            affectedSystems = "Not applicable";

            if (cve.getConfigurations() != null) {

                affectedSystems = cve
                        .getConfigurations()
                        .get(0)
                        .getNodes()
                        .get(0)
                        .getCpeMatch()
                        .get(0)
                        .getCriteria();
            }
        }

        return new CveData(severity, score, affectedSystems);
    }

    private User getCurrentUser() {
        String username = getCurrentUsername();

        return userRepository.findByUsername(username).orElseThrow(() -> new AccessDeniedException("Access denied"));
    }

    private String getCurrentUsername() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AccessDeniedException("Access is denied !");
        }

        final Object principal = authentication.getPrincipal();

        if (principal instanceof User user) {
            return user.getUsername();
        }

        if (principal instanceof Jwt jwt) {
            return jwt.getSubject();
        }

        return principal.toString();
    }

    private void checkNotAlreadyLiked(User currentUser, News newsToLike) {
        boolean alreadyLiked = newsLikeService.checkAlreadyLiked(currentUser, newsToLike);
        if (alreadyLiked) {
            throw new IllegalArgumentException("News already liked !");
        }
    }

    private void checkIsLiked(User currentUser, News newsToUnlike) {
        boolean isLiked = newsLikeService.checkAlreadyLiked(currentUser, newsToUnlike);
        if (!isLiked) {
            throw new IllegalArgumentException("News not liked !");
        }
    }
}
