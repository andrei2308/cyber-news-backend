package com.example.demo.domain.news.dtos;

import com.example.demo.domain.enums.Severity;

public class CveData {
    private Severity severity;
    private Double score;
    private String affectedSystems;

    public CveData() {
    }

    public CveData(Severity severity, Double score, String affectedSystems) {
        this.severity = severity;
        this.score = score;
        this.affectedSystems = affectedSystems;
    }

    // Getters and Setters
    public Severity getSeverity() {
        return severity;
    }

    public void setSeverity(Severity severity) {
        this.severity = severity;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public String getAffectedSystems() {
        return affectedSystems;
    }

    public void setAffectedSystems(String affectedSystems) {
        this.affectedSystems = affectedSystems;
    }
}
