package com.feedup.feedup.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.feedup.feedup.dto.ReportResponse;
import com.feedup.feedup.entity.Feedback;
import com.feedup.feedup.entity.FeedbackSession;
import com.feedup.feedup.repository.FeedbackRepository;
import com.feedup.feedup.service.FeedbackSessionService;
import com.feedup.feedup.service.ScoreService;

@RestController
@RequestMapping("/api/report")
@CrossOrigin("*")
public class ReportController {

    private final FeedbackSessionService sessionService;
    private final FeedbackRepository feedbackRepository;
    private final ScoreService scoreService;

    public ReportController(
            FeedbackSessionService sessionService,
            FeedbackRepository feedbackRepository,
            ScoreService scoreService) {
        this.sessionService = sessionService;
        this.feedbackRepository = feedbackRepository;
        this.scoreService = scoreService;
    }

    @GetMapping("/{sessionId}")
    public ReportResponse getReport(@PathVariable String sessionId) {
        FeedbackSession session = sessionService.getSession(sessionId);
        List<Feedback> feedbacks = feedbackRepository.findBySessionId(sessionId);
        return new ReportResponse(
                session.getSessionName(),
                feedbacks.size(),
                scoreService.buildScores(feedbacks)
        );
    }
}
