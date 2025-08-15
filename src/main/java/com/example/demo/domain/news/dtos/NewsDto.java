package com.example.demo.domain.news.dtos;

import com.example.demo.domain.enums.Severity;

import java.util.Date;

public class NewsDto {
    public String description;
    public String title;
    public Severity severity;
    public String userId;
    public Date createdAt;
    public String authorUsername;
}
