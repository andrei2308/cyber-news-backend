package com.example.demo.domain.news.dtos;

import com.example.demo.domain.enums.Severity;
import com.example.demo.domain.user.dtos.UserProfile;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class NewsDto {
    String id;
    String description;
    String title;
    Severity severity;
    String userId;
    Date createdDate;
    String authorUsername;
    String affectedSystems;
    double score;

    Set<UserProfile> userLikes = new HashSet<>();

}
