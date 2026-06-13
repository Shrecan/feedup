package com.feedup.feedup.dto;

import java.time.LocalDateTime;

import com.feedup.feedup.entity.FeedbackSession;

public record DashboardResponse(
        Long id,
        String sessionName,
        String sessionLink,
        LocalDateTime createdAt,
        LocalDateTime expiresAt,
        boolean active,
        long feedbackCount
) {
    public static DashboardResponse from(FeedbackSession session, long feedbackCount) {
        return new DashboardResponse(
                session.getId(),
                session.getSessionName(),
                session.getSessionLink(),
                session.getCreatedAt(),
                session.getExpiresAt(),
                session.isActive(),
                feedbackCount
        );
    }
}
