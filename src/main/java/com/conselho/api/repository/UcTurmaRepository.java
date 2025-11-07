package com.conselho.api.repository;

import com.conselho.api.model.UcProfessor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UcTurmaRepository extends JpaRepository<UcProfessor, Long> {

    List<UcProfessor> findByConselhoId(Long idConselho);
    List<UcProfessor> findByProfessorId(Long idProfessor);
    List<UcProfessor> findByUnidadeCurricularId(Long idUnidadeCurricular);

}
