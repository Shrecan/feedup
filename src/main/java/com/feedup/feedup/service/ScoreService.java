package com.feedup.feedup.service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.ToIntFunction;

import org.springframework.stereotype.Service;

import com.feedup.feedup.dto.CategoryScore;
import com.feedup.feedup.dto.MetricScore;
import com.feedup.feedup.entity.Feedback;

@Service
public class ScoreService {

    private record MetricDefinition(
            String key,
            String label,
            ToIntFunction<Feedback> reader
    ) {
    }

    private record CategoryDefinition(
            String name,
            List<MetricDefinition> metrics
    ) {
    }

    private final List<CategoryDefinition> categories = List.of(
            new CategoryDefinition("Cafeteria", List.of(
                    new MetricDefinition("hygiene", "Hygiene", Feedback::getHygiene),
                    new MetricDefinition("taste", "Taste", Feedback::getTaste),
                    new MetricDefinition("ingredients", "Ingredients", Feedback::getIngredients),
                    new MetricDefinition("service", "Service", Feedback::getService),
                    new MetricDefinition("quantity", "Quantity", Feedback::getQuantity)
            )),
            new CategoryDefinition("Transport", List.of(
                    new MetricDefinition("safety", "Safety", Feedback::getSafety),
                    new MetricDefinition("availability", "Availability", Feedback::getAvailability),
                    new MetricDefinition("driver", "Driver", Feedback::getDriver),
                    new MetricDefinition("cab", "Cab", Feedback::getCab)
            )),
            new CategoryDefinition("Housekeeping", List.of(
                    new MetricDefinition("cleanliness", "Cleanliness", Feedback::getCleanliness),
                    new MetricDefinition("humidity", "Humidity", Feedback::getHumidity),
                    new MetricDefinition("lighting", "Lighting", Feedback::getLighting),
                    new MetricDefinition("response", "Response", Feedback::getResponse)
            ))
    );

    public List<CategoryScore> buildScores(List<Feedback> feedbacks) {
        List<CategoryScore> result = new ArrayList<>();
        for (CategoryDefinition category : categories) {
            List<MetricScore> metrics = new ArrayList<>();
            for (MetricDefinition metric : category.metrics()) {
                double score = averageMetric(feedbacks, metric.reader());
                metrics.add(new MetricScore(metric.key(), metric.label(), score, color(score)));
            }
            double categoryScore = metrics.stream()
                    .mapToDouble(MetricScore::score)
                    .average()
                    .orElse(0);
            result.add(new CategoryScore(category.name(), round(categoryScore), color(categoryScore), metrics));
        }
        return result;
    }

    private double averageMetric(List<Feedback> feedbacks, ToIntFunction<Feedback> reader) {
        if (feedbacks.isEmpty()) {
            return 0;
        }
        double score = feedbacks.stream()
                .mapToInt(reader)
                .average()
                .orElse(0) * 12.5;
        return round(score);
    }

    private double round(double value) {
        return Math.round(value * 10.0) / 10.0;
    }

    private String color(double score) {
        if (score >= 90) {
            return "green";
        }
        if (score >= 70) {
            return "amber";
        }
        return "red";
    }
}
