package com.example.demo.service.userLike;

import com.example.demo.domain.news.entity.News;
import com.example.demo.domain.user.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface NewsLikeService {
    void like(User currentUser, News newsToLike);

    void unlike(User currentUser, News newsToUnlike);

    boolean checkAlreadyLiked(User currentUser, News newsToLike);
}
