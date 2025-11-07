package com.conselho.api.repository;

import com.conselho.api.model.feedback.ConselhoTurmaFeedback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConselhoTurmaFeedbackRepository extends JpaRepository<ConselhoTurmaFeedback, Long> {
    boolean existsByConselhoId(Long idConselho);
}
