package com.example.demo.domain.newsLikes.entity;

import com.example.demo.domain.InformationEntity;
import com.example.demo.domain.news.entity.News;
import com.example.demo.domain.user.entity.User;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "news_likes")
@Getter
@Setter
public class NewsLikes extends InformationEntity {

    @ManyToOne
    @JoinColumn(name = "post_id")
    private News likedPost;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User likedUser;
}
