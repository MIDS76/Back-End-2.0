package com.conselho.api.repository;

import com.conselho.api.model.turma.Turma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TurmaRepository extends JpaRepository<Turma,Long> {

    boolean existsByNome(String nome);
    List<String> listaNomesAlunos(List<Long> ids);
}
