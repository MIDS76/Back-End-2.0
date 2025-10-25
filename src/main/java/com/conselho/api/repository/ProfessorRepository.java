package com.conselho.api.repository;

import com.conselho.api.model.Professor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfessorRepository extends JpaRepository<Professor,Long> {

    boolean existsByNome(String nome);
}
