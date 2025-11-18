package com.conselho.api.repository.feedback;

import com.conselho.api.model.AlunoTurma;
import com.conselho.api.model.entity.Aluno;
import com.conselho.api.model.feedback.ConselhoAlunoFeedback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConselhoAlunoFeedbackRepository extends JpaRepository<ConselhoAlunoFeedback, Long> {
    boolean existsByConselhoId(Long idConselho);
    ConselhoAlunoFeedback findByAluno(Aluno aluno);
}
