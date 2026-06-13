package com.feedup.feedup.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.feedup.feedup.entity.FeedbackSession;

public interface FeedbackSessionRepository
        extends JpaRepository<FeedbackSession, Long> {

    Optional<FeedbackSession> findBySessionLink(
            String sessionLink
    );

    List<FeedbackSession> findByActiveTrue();

    List<FeedbackSession>
    findAllByOrderByCreatedAtDesc();
}
