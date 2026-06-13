package com.feedup.feedup.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.feedup.feedup.entity.Feedback;
import com.feedup.feedup.repository.FeedbackRepository;

@Service
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final FeedbackSessionService sessionService;

    public FeedbackService(
            FeedbackRepository feedbackRepository,
            FeedbackSessionService sessionService) {
        this.feedbackRepository = feedbackRepository;
        this.sessionService = sessionService;
    }

    public Feedback saveFeedback(Feedback feedback) {

        sessionService.getOpenSession(feedback.getSessionId());

        if (feedback.getName() == null
                || feedback.getName().isBlank()) {

            throw new RuntimeException(
                    "Name is required."
            );
        }

        if (feedback.getEmail() == null
                || feedback.getEmail().isBlank()) {

            throw new RuntimeException(
                    "Email is required."
            );
        }

  if (feedback.getHygiene() < 1
    || feedback.getTaste() < 1
    || feedback.getIngredients() < 1
    || feedback.getService() < 1
    || feedback.getQuantity() < 1
    || feedback.getSafety() < 1
    || feedback.getAvailability() < 1
    || feedback.getDriver() < 1
    || feedback.getCab() < 1
    || feedback.getCleanliness() < 1
    || feedback.getHumidity() < 1
    || feedback.getLighting() < 1
    || feedback.getResponse() < 1) {

    throw new RuntimeException(
            "Please answer all questions"
    );
}
        boolean alreadySubmitted =
                feedbackRepository
                        .existsBySessionIdAndEmail(
                                feedback.getSessionId(),
                                feedback.getEmail()
                        );

        if (alreadySubmitted) {

            throw new RuntimeException(
                    "You have already submitted feedback for this session."
            );
        }

        feedback.setSubmittedAt(LocalDateTime.now());
        return feedbackRepository.save(feedback);
    }

    public long getTotalFeedbacks() {
        return feedbackRepository.count();
    }
}
