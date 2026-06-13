package com.feedup.feedup.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.feedup.feedup.dto.DashboardResponse;
import com.feedup.feedup.dto.PasswordRequest;
import com.feedup.feedup.entity.FeedbackSession;
import com.feedup.feedup.service.AdminAuthService;
import com.feedup.feedup.service.FeedbackSessionService;

@RestController
@CrossOrigin("*")
public class SessionAliasController {

    private final FeedbackSessionService sessionService;
    private final AdminAuthService adminAuthService;

    public SessionAliasController(
            FeedbackSessionService sessionService,
            AdminAuthService adminAuthService) {
        this.sessionService = sessionService;
        this.adminAuthService = adminAuthService;
    }

    @DeleteMapping("/session/{id}")
    public ResponseEntity<?> deleteSession(
            @PathVariable Long id,
            @RequestBody PasswordRequest request) {
        if (!adminAuthService.validPassword(request.password())) {
            return ResponseEntity.status(401).body("Invalid password");
        }
        sessionService.deleteSession(id);
        return ResponseEntity.ok("Session deleted");
    }

    @PostMapping("/session/expire/{id}")
    public ResponseEntity<DashboardResponse> expireSession(@PathVariable Long id) {
        FeedbackSession session = sessionService.expireSession(id);
        return ResponseEntity.ok(DashboardResponse.from(
                session,
                sessionService.countFeedbacks(session.getSessionLink())));
    }
}
