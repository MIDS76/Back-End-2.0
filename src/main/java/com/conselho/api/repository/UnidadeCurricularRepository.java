package com.conselho.api.repository;

import com.conselho.api.model.unidadeCurricular.UnidadeCurricular;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UnidadeCurricularRepository extends JpaRepository<UnidadeCurricular, Long> {

    boolean existsByNome(String nome);
}
