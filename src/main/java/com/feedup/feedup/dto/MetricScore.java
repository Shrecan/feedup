package com.feedup.feedup.dto;

public record MetricScore(
        String key,
        String label,
        double score,
        String color
) {
}
