package com.conselho.api.repository;

import com.conselho.api.model.Turma;
import com.conselho.api.model.conselho.Conselho;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ConselhoRepository extends JpaRepository<Conselho, Long> {

    List<Conselho> findByTurmaId(Long idTurma);
    Conselho findTopByTurmaIdOrderByDataInicioDesc(Long turmaId);
}
