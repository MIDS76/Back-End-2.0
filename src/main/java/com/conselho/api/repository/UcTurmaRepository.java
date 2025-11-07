package com.conselho.api.repository;

import com.conselho.api.model.UcTurma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UcTurmaRepository extends JpaRepository<UcTurma, Long> {

    List<UcTurma> findByConselhoId(Long idConselho);
    List<UcTurma> findByProfessorId(Long idProfessor);
    List<UcTurma> findByUnidadeCurricularId(Long idUnidadeCurricular);

}
