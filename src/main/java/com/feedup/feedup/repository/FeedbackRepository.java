package com.feedup.feedup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.feedup.feedup.entity.Feedback;

public interface FeedbackRepository
        extends JpaRepository<Feedback, Long> {

    boolean existsBySessionIdAndEmail(
            String sessionId,
            String email
    );

    long countBySessionId(
            String sessionId
    );

    List<Feedback> findBySessionId(
            String sessionId
    );

    List<Feedback> findBySessionIdOrderBySubmittedAtDesc(
            String sessionId
    );

    List<Feedback> findAllByOrderBySubmittedAtDesc();

    void deleteBySessionId(
            String sessionId
    );
}
