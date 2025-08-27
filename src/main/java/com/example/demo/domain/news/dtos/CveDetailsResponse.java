package com.example.demo.domain.news.dtos;

public class CveDetailsResponse {
    private boolean exists;
    private CveData data;
    private String error;

    public CveDetailsResponse() {
    }

    public CveDetailsResponse(boolean exists, CveData data, String error) {
        this.exists = exists;
        this.data = data;
        this.error = error;
    }

    // Getters and Setters
    public boolean isExists() {
        return exists;
    }

    public void setExists(boolean exists) {
        this.exists = exists;
    }

    public CveData getData() {
        return data;
    }

    public void setData(CveData data) {
        this.data = data;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}