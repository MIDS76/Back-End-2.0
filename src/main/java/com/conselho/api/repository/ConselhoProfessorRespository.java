package com.conselho.api.repository;

import com.conselho.api.model.ConselhoProfessor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConselhoProfessorRespository extends JpaRepository<ConselhoProfessor, Long> {
    List<ConselhoProfessor> findByConselhoId(Long idConselho);
}
