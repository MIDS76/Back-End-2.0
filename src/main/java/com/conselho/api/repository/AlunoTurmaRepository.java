package com.conselho.api.repository;

import com.conselho.api.model.AlunoTurma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlunoTurmaRepository extends JpaRepository<AlunoTurma,Long> {

    List<AlunoTurma> findByTurmaId(Long idTurma);
}
