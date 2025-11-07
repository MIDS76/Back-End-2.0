package com.conselho.api.repository;

import com.conselho.api.model.PreConselhoPedagogico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PreConselhoPedagogicoRepository extends JpaRepository<PreConselhoPedagogico, Long> {
    boolean existsByPreConselhoId(Long idPreConselho);
}
