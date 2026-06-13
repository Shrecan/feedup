package com.feedup.feedup.dto;

import java.util.List;

public record AnalyticsResponse(
        String sessionName,
        long feedbackCount,
        List<CategoryScore> categories
) {
}
