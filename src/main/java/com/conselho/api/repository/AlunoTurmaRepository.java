package com.conselho.api.repository;

import com.conselho.api.model.AlunoTurma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AlunoTurmaRepository extends JpaRepository<AlunoTurma,Long> {

    List<AlunoTurma> findByTurmaId(Long idTurma);

   Optional<AlunoTurma> findByAlunoId(Long idAluno);

    @Query("SELECT at.aluno.id FROM AlunoTurma at WHERE at.turma.id = :idTurma")
    List<Long> findAllAlunosByTurma(@Param("idTurma") Long idTurma);
}
