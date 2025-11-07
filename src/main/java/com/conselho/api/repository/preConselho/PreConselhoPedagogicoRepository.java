package com.conselho.api.repository.preConselho;

import com.conselho.api.model.preConselho.PreConselhoPedagogico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PreConselhoPedagogicoRepository extends JpaRepository<PreConselhoPedagogico, Long> {
    boolean existsByPreConselhoId(Long idPreConselho);
}
