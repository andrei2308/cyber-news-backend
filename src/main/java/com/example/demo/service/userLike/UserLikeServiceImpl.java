package com.example.demo.service.userLike;

import com.example.demo.domain.news.entity.News;
import com.example.demo.domain.newsLikes.entity.NewsLikes;
import com.example.demo.domain.newsLikes.repository.UserLikeRepository;
import com.example.demo.domain.user.entity.User;
import org.springframework.stereotype.Service;

@Service
public class UserLikeServiceImpl implements UserLikeService {

    private final UserLikeRepository userLikeRepository;

    public UserLikeServiceImpl(UserLikeRepository userLikeRepository) {
        this.userLikeRepository = userLikeRepository;
    }

    @Override
    public void like(User currentUser, News newsToLike) {
        NewsLikes newsLikes = new NewsLikes();
        newsLikes.setLikedPost(newsToLike);
        newsLikes.setLikedUser(currentUser);

        userLikeRepository.save(newsLikes);
    }

    @Override
    public void unlike(User currentUser, News newsToUnlike) {
        String likedNewsAndUserId = userLikeRepository.findLikedNewsByNewsIdAndUserId(currentUser.getId(), newsToUnlike.getId());

        userLikeRepository.deleteById(likedNewsAndUserId);
    }
}
