package com.feedup.feedup.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.feedup.feedup.entity.Feedback;
import com.feedup.feedup.service.FeedbackService;

@RestController
@RequestMapping("/api/feedback")
@CrossOrigin("*")
public class FeedbackController {

    private final FeedbackService feedbackService;

    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @PostMapping("/submit")
    public ResponseEntity<?> submitFeedback(
            @RequestBody Feedback feedback) {

        try {

            feedbackService.saveFeedback(
                    feedback
            );

            return ResponseEntity.ok(
                    "Feedback Submitted Successfully"
            );

        } catch (RuntimeException e) {

            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    @GetMapping("/count")
    public long getTotalFeedbacks() {

        return feedbackService
                .getTotalFeedbacks();
    }
}
