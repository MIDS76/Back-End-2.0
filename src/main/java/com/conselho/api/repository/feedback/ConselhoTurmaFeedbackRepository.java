package com.conselho.api.repository.feedback;

import com.conselho.api.model.conselho.Conselho;
import com.conselho.api.model.feedback.ConselhoTurmaFeedback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConselhoTurmaFeedbackRepository extends JpaRepository<ConselhoTurmaFeedback, Long> {
    boolean existsByConselhoId(Long idConselho);
    ConselhoTurmaFeedback findByConselho(Conselho conselho);
}
