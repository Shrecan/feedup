package com.feedup.feedup.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.feedup.feedup.entity.FeedbackSession;
import com.feedup.feedup.repository.FeedbackRepository;
import com.feedup.feedup.repository.FeedbackSessionRepository;

@Service
public class FeedbackSessionService {

    private final FeedbackSessionRepository repository;
    private final FeedbackRepository feedbackRepository;

    public FeedbackSessionService(
            FeedbackSessionRepository repository,
            FeedbackRepository feedbackRepository) {
        this.repository = repository;
        this.feedbackRepository = feedbackRepository;
    }

    @Transactional
    public FeedbackSession createSession(String sessionName) {

        for (FeedbackSession activeSession : repository.findByActiveTrue()) {
            activeSession.setActive(false);
            repository.save(activeSession);
        }

        FeedbackSession session =
                new FeedbackSession();

        session.setSessionName(
                sessionName
        );

        return repository.save(
                session
        );
    }

    @Transactional
    public FeedbackSession getSession(
            String sessionLink) {

        FeedbackSession session =
                repository
                        .findBySessionLink(
                                sessionLink
                        )
                        .orElseThrow(() -> new RuntimeException("Session not found"));

        if (session.getExpiresAt()
                .isBefore(
                        LocalDateTime.now()
                )) {

            session.setActive(false);

            repository.save(session);
        }

        return session;
    }

    @Transactional
    public FeedbackSession deleteSession(Long id) {
        FeedbackSession session = repository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Session not found"));
        feedbackRepository.deleteBySessionId(session.getSessionLink());
        repository.delete(session);
        return session;
    }

    @Transactional
    public FeedbackSession expireSession(Long id) {
        FeedbackSession session = repository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Session not found"));
        session.setActive(false);
        session.setExpiresAt(LocalDateTime.now());
        return repository.save(session);
    }

    @Transactional
    public FeedbackSession getOpenSession(String sessionLink) {
        FeedbackSession session = getSession(sessionLink);
        if (!session.isActive()) {
            throw new RuntimeException("This feedback session is inactive.");
        }
        if (session.getExpiresAt().isBefore(LocalDateTime.now())) {
            session.setActive(false);
            repository.save(session);
            throw new RuntimeException("This feedback session has expired.");
        }
        return session;
    }

    public List<FeedbackSession> getAllSessions() {
        expireOldSessions();

        return repository
                .findAllByOrderByCreatedAtDesc();
    }

    public long countFeedbacks(String sessionLink) {
        return feedbackRepository.countBySessionId(sessionLink);
    }

    @Transactional
    public void expireOldSessions() {
        for (FeedbackSession session : repository.findByActiveTrue()) {
            if (session.getExpiresAt().isBefore(LocalDateTime.now())) {
                session.setActive(false);
                repository.save(session);
            }
        }
    }
}
