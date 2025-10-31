package com.conselho.api.repository;
import com.conselho.api.model.ConselhoAluno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConselhoAlunoRepository extends JpaRepository<ConselhoAluno, Long> {
    boolean  existsById(Long id);

    List<ConselhoAluno> findByConselhoId(Long idConselho);

    List<ConselhoAluno> findByAlunoId(Long idAluno);
}
