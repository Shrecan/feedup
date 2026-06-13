package com.feedup.feedup.dto;

import java.time.LocalDateTime;

import com.feedup.feedup.entity.FeedbackSession;

public record SessionResponse(
        Long id,
        String sessionName,
        String sessionLink,
        String clientUrl,
        LocalDateTime createdAt,
        LocalDateTime expiresAt,
        boolean active
) {
    public static SessionResponse from(FeedbackSession session, String baseUrl) {
        String clientUrl = baseUrl + "/client.html?session=" + session.getSessionLink();
        return new SessionResponse(
                session.getId(),
                session.getSessionName(),
                session.getSessionLink(),
                clientUrl,
                session.getCreatedAt(),
                session.getExpiresAt(),
                session.isActive()
        );
    }
}
