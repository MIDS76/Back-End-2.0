package com.conselho.api.repository;

import com.conselho.api.dto.response.preConselho.PreConselhoProfessorResponseDTO;
import com.conselho.api.model.preConselho.PreConselhoProfessor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PreConselhoProfessorRepository extends JpaRepository<PreConselhoProfessor, Long> {

    List<PreConselhoProfessor> findByPreConselhoId(Long idPreConselho);
}
