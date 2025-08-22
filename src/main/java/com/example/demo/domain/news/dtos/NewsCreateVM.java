package com.example.demo.domain.news.dtos;

import com.example.demo.domain.enums.Severity;
import org.jetbrains.annotations.NotNull;

public class NewsCreateVM {
    public String description;
    public String title;
    public Severity severity;
    public String affectedSystems;
    public double score;
}
