package com.conselho.api.repository;

import com.conselho.api.model.Professor;
import com.conselho.api.model.Supervisor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupervisorRepository extends JpaRepository<Supervisor,Long> {

    boolean existsByNome(String nome);
}
