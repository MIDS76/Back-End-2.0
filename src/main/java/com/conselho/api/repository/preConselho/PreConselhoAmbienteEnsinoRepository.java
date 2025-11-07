package com.conselho.api.repository.preConselho;

import com.conselho.api.model.preConselho.PreConselhoAmbienteEnsino;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PreConselhoAmbienteEnsinoRepository extends JpaRepository<PreConselhoAmbienteEnsino, Long> {
    boolean existsByPreConselhoId(Long idPreConselho);
}
