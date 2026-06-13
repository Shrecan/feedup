package com.feedup.feedup.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.feedup.feedup.dto.DashboardResponse;
import com.feedup.feedup.dto.PasswordRequest;
import com.feedup.feedup.dto.SessionCreateRequest;
import com.feedup.feedup.dto.SessionResponse;
import com.feedup.feedup.entity.FeedbackSession;
import com.feedup.feedup.service.AdminAuthService;
import com.feedup.feedup.service.FeedbackSessionService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/session")
@CrossOrigin("*")
public class SessionController {

    private final FeedbackSessionService sessionService;
    private final AdminAuthService adminAuthService;

    public SessionController(
            FeedbackSessionService sessionService,
            AdminAuthService adminAuthService) {
        this.sessionService = sessionService;
        this.adminAuthService = adminAuthService;
    }

    @PostMapping("/create")
    public ResponseEntity<SessionResponse> createSession(
            @Valid @RequestBody SessionCreateRequest request,
            HttpServletRequest servletRequest) {
        FeedbackSession session = sessionService.createSession(request.sessionName());
        return ResponseEntity.ok(SessionResponse.from(session, baseUrl(servletRequest)));
    }

    @GetMapping("/{sessionId}")
    public ResponseEntity<SessionResponse> getSession(
            @PathVariable String sessionId,
            HttpServletRequest servletRequest) {
        FeedbackSession session = sessionService.getSession(sessionId);
        return ResponseEntity.ok(SessionResponse.from(session, baseUrl(servletRequest)));
    }

    @GetMapping("/all")
    public List<DashboardResponse> getAllSessions() {
        return sessionService.getAllSessions()
                .stream()
                .map(session -> DashboardResponse.from(
                        session,
                        sessionService.countFeedbacks(session.getSessionLink())))
                .toList();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSession(
            @PathVariable Long id,
            @RequestBody PasswordRequest request) {
        if (!adminAuthService.validPassword(request.password())) {
            return ResponseEntity.status(401).body("Invalid password");
        }
        sessionService.deleteSession(id);
        return ResponseEntity.ok("Session deleted");
    }

    @PostMapping("/expire/{id}")
    public ResponseEntity<DashboardResponse> expireSession(@PathVariable Long id) {
        FeedbackSession session = sessionService.expireSession(id);
        return ResponseEntity.ok(DashboardResponse.from(
                session,
                sessionService.countFeedbacks(session.getSessionLink())));
    }

    private String baseUrl(HttpServletRequest request) {
        return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
    }
}
