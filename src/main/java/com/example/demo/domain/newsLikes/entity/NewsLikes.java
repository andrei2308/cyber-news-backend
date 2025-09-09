package com.example.demo.domain.newsLikes.entity;

import com.example.demo.domain.InformationEntity;
import com.example.demo.domain.news.entity.News;
import com.example.demo.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "news_likes")
@Getter
@Setter
public class NewsLikes extends InformationEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private News likedPost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User likedUser;
}
