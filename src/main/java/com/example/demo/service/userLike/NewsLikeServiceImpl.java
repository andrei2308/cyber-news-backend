package com.example.demo.service.userLike;

import com.example.demo.domain.news.entity.News;
import com.example.demo.domain.newsLikes.entity.NewsLikes;
import com.example.demo.domain.newsLikes.repository.NewsLikeRepository;
import com.example.demo.domain.user.entity.User;
import org.springframework.stereotype.Service;

@Service
public class NewsLikeServiceImpl implements NewsLikeService {

    private final NewsLikeRepository newsLikeRepository;

    public NewsLikeServiceImpl(NewsLikeRepository userLikeRepository) {
        this.newsLikeRepository = userLikeRepository;
    }

    @Override
    public void like(User currentUser, News newsToLike) {
        NewsLikes newsLikes = new NewsLikes();
        newsLikes.setLikedPost(newsToLike);
        newsLikes.setLikedUser(currentUser);

        newsLikeRepository.save(newsLikes);
    }

    @Override
    public void unlike(User currentUser, News newsToUnlike) {
        String likedNewsAndUserId = newsLikeRepository.findLikedNewsByNewsIdAndUserId(currentUser.getId(), newsToUnlike.getId());

        newsLikeRepository.deleteById(likedNewsAndUserId);
    }

    @Override
    public boolean checkAlreadyLiked(User currentUser, News newsToLike) {
        return newsLikeRepository.existsByLikedUserAndLikedPost(currentUser, newsToLike);
    }
}
