package com.feedup.feedup.service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.feedup.feedup.dto.GlobalCommentResponse;
import com.feedup.feedup.entity.Feedback;
import com.feedup.feedup.entity.FeedbackSession;
import com.feedup.feedup.repository.FeedbackRepository;
import com.feedup.feedup.repository.FeedbackSessionRepository;

@Service
public class CommentService {

    private final FeedbackRepository feedbackRepository;
    private final FeedbackSessionRepository sessionRepository;

    public CommentService(
            FeedbackRepository feedbackRepository,
            FeedbackSessionRepository sessionRepository) {
        this.feedbackRepository = feedbackRepository;
        this.sessionRepository = sessionRepository;
    }

    public List<GlobalCommentResponse> getAllComments() {
        Map<String, FeedbackSession> sessions = sessionRepository.findAll()
                .stream()
                .collect(Collectors.toMap(
                        FeedbackSession::getSessionLink,
                        Function.identity(),
                        (first, second) -> first));

        return feedbackRepository.findAllByOrderBySubmittedAtDesc()
                .stream()
                .map(feedback -> toResponse(feedback, sessions.get(feedback.getSessionId())))
                .toList();
    }

    private GlobalCommentResponse toResponse(Feedback feedback, FeedbackSession session) {
        return new GlobalCommentResponse(
                feedback.getId(),
                feedback.getName(),
                feedback.getEmail(),
                session == null ? "Deleted Session" : session.getSessionName(),
                feedback.getSessionId(),
                feedback.getSubmittedAt(),
                ratingSummary(feedback),
                feedback.getComments()
        );
    }

    private double ratingSummary(Feedback feedback) {
        double total = feedback.getHygiene()
                + feedback.getTaste()
                + feedback.getIngredients()
                + feedback.getService()
                + feedback.getQuantity()
                + feedback.getSafety()
                + feedback.getAvailability()
                + feedback.getDriver()
                + feedback.getCab()
                + feedback.getCleanliness()
                + feedback.getHumidity()
                + feedback.getLighting()
                + feedback.getResponse();
        return Math.round((total / 13.0) * 12.5 * 10.0) / 10.0;
    }
}
