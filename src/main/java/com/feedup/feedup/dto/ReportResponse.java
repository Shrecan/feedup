package com.feedup.feedup.dto;

import java.util.List;

public record ReportResponse(
        String sessionName,
        long feedbackCount,
        List<CategoryScore> categories
) {
}
