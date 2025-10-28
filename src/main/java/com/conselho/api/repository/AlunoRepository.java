package com.conselho.api.repository;

import com.conselho.api.model.aluno.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlunoRepository extends JpaRepository<Aluno, Long> {

    boolean existsByNome(String nome);
    boolean existsById(Long idAluno);
    Aluno findByRepresentanteTrue();
    boolean existsByIdAndRepresentanteTrue(Long id);
}
