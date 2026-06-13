package com.feedup.feedup.dto;

import java.util.List;

public record CategoryScore(
        String category,
        double score,
        String color,
        List<MetricScore> metrics
) {
}
