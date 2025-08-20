package com.example.demo.domain.news.dtos;

import com.example.demo.domain.enums.Severity;
import org.jetbrains.annotations.NotNull;

import java.util.Date;

public class NewsDto {
    @NotNull
    String description;
    @NotNull
    String title;
    @NotNull
    Severity severity;
    String userId;
    Date createdDate;
    String authorUsername;

    @NotNull
    public Severity getSeverity() {
        return severity;
    }

    public void setSeverity(@NotNull Severity severity) {
        this.severity = severity;
    }

    @NotNull
    public String getDescription() {
        return description;
    }

    public void setDescription(@NotNull String description) {
        this.description = description;
    }

    @NotNull
    public String getTitle() {
        return title;
    }

    public void setTitle(@NotNull String title) {
        this.title = title;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdAt) {
        this.createdDate = createdAt;
    }

    public String getAuthorUsername() {
        return authorUsername;
    }

    public void setAuthorUsername(String authorUsername) {
        this.authorUsername = authorUsername;
    }
}
