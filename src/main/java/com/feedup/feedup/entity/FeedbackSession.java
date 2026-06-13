package com.feedup.feedup.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "sessions")
public class FeedbackSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String sessionName;

    @Column(nullable = false, unique = true)
    private String sessionLink;

    private LocalDateTime createdAt;

    private LocalDateTime expiresAt;

    private boolean active;

    public FeedbackSession() {
        this.createdAt = LocalDateTime.now();
        this.expiresAt = createdAt.plusDays(1);
        this.active = true;
        this.sessionLink = UUID.randomUUID().toString();
    }

    public Long getId() {
        return id;
    }

    public String getSessionName() {
        return sessionName;
    }

    public String getSessionLink() {
        return sessionLink;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public boolean isActive() {
        return active;
    }

    // SETTERS

    public void setSessionName(String sessionName) {
        this.sessionName = sessionName;
    }

    public void setSessionLink(String sessionLink) {
        this.sessionLink = sessionLink;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
