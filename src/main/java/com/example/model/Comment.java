package com.example.model;

import java.sql.Timestamp;

public class Comment {
    private Long id;
    private String title;
    private String content;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Timestamp deletetedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Timestamp getDeletetedAt() {
        return deletetedAt;
    }

    public void setDeletetedAt(Timestamp deletetedAt) {
        this.deletetedAt = deletetedAt;
    }
}
