package com.conselho.api.repository.preConselho;

import com.conselho.api.model.preConselho.PreConselho;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface PreConselhoRepository extends JpaRepository<PreConselho, Long> {
    boolean existsByConselhoId(Long idConselho);

    List<PreConselho> findByConselhoId(Long conselhoId);
}
