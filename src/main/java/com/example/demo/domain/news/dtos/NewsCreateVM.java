package com.example.demo.domain.news.dtos;

import com.example.demo.domain.enums.Severity;

public class NewsCreateVM {
    String description;
    String title;
    Severity severity;
    String affectedSystems;
    double score;

    public String getAffectedSystems() {
        return affectedSystems;
    }

    public void setAffectedSystems(String affectedSystems) {
        this.affectedSystems = affectedSystems;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Severity getSeverity() {
        return severity;
    }

    public void setSeverity(Severity severity) {
        this.severity = severity;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

}
