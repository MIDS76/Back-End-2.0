package com.conselho.api.repository.preConselho;

import com.conselho.api.model.preConselho.PreConselhoSupervisao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PreConselhoSupervisaoRepository extends JpaRepository<PreConselhoSupervisao, Long> {
    boolean existsByPreConselhoId(Long idPreConselho);

}
