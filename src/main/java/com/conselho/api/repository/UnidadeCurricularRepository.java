package com.conselho.api.repository;

import com.conselho.api.model.UnidadeCurricular;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UnidadeCurricularRepository extends JpaRepository<UnidadeCurricular, Long> {

    @Query("SELECT uc.nome FROM UnidadeCurricular uc WHERE uc.id IN :ids")
    List<String> findNomesByIds(@Param("ids") List<Long> ids);

    List<UnidadeCurricular> findByIdIn(List<Long> ids);

    boolean existsByNome(String nome);
}
