package com.conselho.api.repository;

import com.conselho.api.model.PreConselho;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PreConselhoRepository extends JpaRepository<PreConselho, Long> {
    boolean existsByConselhoId(Long idConselho);
}
