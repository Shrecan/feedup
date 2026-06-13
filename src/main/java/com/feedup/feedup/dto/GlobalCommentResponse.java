package com.feedup.feedup.dto;

import java.time.LocalDateTime;

public record GlobalCommentResponse(
        Long feedbackId,
        String name,
        String email,
        String sessionName,
        String sessionLink,
        LocalDateTime timestamp,
        double ratingSummary,
        String comments
) {
}
