package com.conselho.api.repository.feedback;

import com.conselho.api.model.AlunoTurma;
import com.conselho.api.model.conselho.Conselho;
import com.conselho.api.model.entity.Aluno;
import com.conselho.api.model.feedback.ConselhoAlunoFeedback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConselhoAlunoFeedbackRepository extends JpaRepository<ConselhoAlunoFeedback, Long> {
    boolean existsByConselhoId(Long idConselho);
    ConselhoAlunoFeedback findByAlunoAndConselho(Aluno aluno, Conselho conselho);
}
